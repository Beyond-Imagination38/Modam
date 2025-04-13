package com.modam.backend.service;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.model.*;
import com.modam.backend.repository.BookClubRepository;
import com.modam.backend.repository.ChatMessageRepository;
import com.modam.backend.repository.DiscussionTopicRepository;
import com.modam.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final BookClubRepository bookClubRepository;
    private final UserRepository userRepository;
    private final DiscussionTopicRepository discussionTopicRepository;
    private final SimpMessagingTemplate messagingTemplate; // 추가

    @Transactional
    public ChatMessageDto saveChatMessage(int clubId, ChatMessageDto dto) {

        System.out.println("메시지 저장 요청: " + dto);

        BookClub bookClub = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));

        // 유저가 이 club에서 처음 메시지 보내는지 확인
        boolean isFirstMessage = chatMessageRepository
                .findByBookClubOrderByCreatedTimeAsc(bookClub)
                .stream()
                .noneMatch(m -> m.getUser().equals(user));

        MessageType messageType = dto.getMessageType();
        Integer order = null;

        if (messageType == null) {
            if (isFirstMessage) {
                messageType = MessageType.SUBTOPIC;
                order = chatMessageRepository.countByBookClubAndMessageType(bookClub, MessageType.SUBTOPIC) + 1;
                System.out.println("처음 메시지 → SUBTOPIC, 순서: " + order);
            }
            else {
                messageType = MessageType.DISCUSSION;
            }
        }
        ChatMessage chatMessage = ChatMessage.builder()
                .bookClub(bookClub)
                .user(user)
                .content(dto.getContent())
                .messageType(messageType) // 클라이언트 input 무시
                .subtopicOrder(order)
                .build();

        chatMessageRepository.save(chatMessage);

        if (messageType == MessageType.ENTER) {
            long enterCount = chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub)
                    .stream()
                    .filter(m -> m.getMessageType() == MessageType.ENTER)
                    .count();

            if (enterCount == 4) {
                int bookId = bookClub.getBook().getBookId();
                List<String> dummyResponses = List.of(
                        "나는 주인공 윈스턴의 외로움에 공감했어요.",
                        "전체주의 사회의 공포가 정말 생생하게 느껴졌습니다.",
                        "빅브라더의 감시는 현대 사회와도 닮은 것 같아요."
                );
                sendAiMainTopic(clubId, bookId, dummyResponses); //  AI 호출
            }
        }

        return new ChatMessageDto(
                messageType,
                clubId,
                user.getUserId(),
                user.getUserName(),
                dto.getContent(),
                chatMessage.getCreatedTime(),
                order
        );
    }

    @Transactional(readOnly = true)
    public List<ChatMessageDto> getChatHistory(int clubId) {
        BookClub bookClub = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));

        return chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub)
                .stream()
                .map(msg -> new ChatMessageDto(
                        msg.getMessageType(),
                        clubId,
                        msg.getUser().getUserId(),
                        msg.getUser().getUserName(),
                        msg.getContent(),
                        msg.getCreatedTime()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void sendAiMainTopic(int clubId, int bookId, List<String> userResponses) {
        String flaskUrl = "http://localhost:5000/ai/generate-topics";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("book_id", bookId);
        requestBody.put("user_responses", userResponses);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.postForEntity(flaskUrl, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<String> topics = (List<String>) response.getBody().get("topics");

                BookClub bookClub = bookClubRepository.findById(clubId)
                        .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));

                for (int i = 0; i < topics.size(); i++) {
                    String topic = topics.get(i);

                    //  1. DB 저장 (discussion_topic)
                    DiscussionTopic discussionTopic = DiscussionTopic.builder()
                            .club(bookClub)
                            .content(topic)
                            .createdTime(new Timestamp(System.currentTimeMillis()))
                            .version(i + 1)
                            .build();
                    discussionTopicRepository.save(discussionTopic);

                    //  2. WebSocket 전송
                    ChatMessageDto topicMessage = new ChatMessageDto(
                            MessageType.TOPIC_START,
                            clubId,
                            0,
                            "AI 진행자",
                            "대주제 " + (i + 1) + ": " + topic,
                            new Timestamp(System.currentTimeMillis())
                    );

                    messagingTemplate.convertAndSend("/topic/chat/" + clubId, topicMessage);
                }
            }

        } catch (Exception e) {
            System.err.println("AI 토픽 생성 실패: " + e.getMessage());
        }

    }

}
