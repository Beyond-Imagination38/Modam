package com.modam.backend.service;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.model.*;
import com.modam.backend.repository.BookClubRepository;
import com.modam.backend.repository.ChatMessageRepository;
import com.modam.backend.repository.DiscussionTopicRepository;
import com.modam.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private static final String GREETING_MESSAGE = "안녕하세요 이번 모임은 책 1984에 대한 내용입니다. 첫번째 주제는 다음과 같습니다."; // 수정3: 메시지 상수화
    private static final String FIRST_SUBTOPIC_NOTICE = "그럼 사용자 1의 의견에 대해 이야기해봅시다."; // 수정3

    private final ChatMessageRepository chatMessageRepository;
    private final BookClubRepository bookClubRepository;
    private final UserRepository userRepository;
    private final DiscussionTopicRepository discussionTopicRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ChatMessageDto saveChatMessage(int clubId, ChatMessageDto dto) {
        System.out.println("메시지 저장 요청: " + dto);

        BookClub bookClub = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));

        // 수정1: 유저의 첫 메시지 여부 정확히 판단 (ENTER 제외)
        long userMsgCount = chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub)
                .stream()
                .filter(m -> m.getUser().equals(user) && m.getMessageType() != MessageType.ENTER)
                .count();
        boolean isFirstMessage = userMsgCount == 0;

        // 수정1: messageType 강제 분기
        MessageType messageType;
        Integer order = null;
        if (dto.getMessageType() == MessageType.ENTER) {
            messageType = MessageType.ENTER;
        } else if (isFirstMessage) {
            messageType = MessageType.SUBTOPIC;
            order = chatMessageRepository.countByBookClubAndMessageType(bookClub, MessageType.SUBTOPIC) + 1;
        } else {
            messageType = MessageType.DISCUSSION;
        }

        ChatMessage chatMessage = ChatMessage.builder()
                .bookClub(bookClub)
                .user(user)
                .content(dto.getContent())
                .messageType(messageType)
                .subtopicOrder(order)
                .build();
        chatMessageRepository.save(chatMessage);

        // 수정2: ENTER 처리 후 count 및 토픽 호출 - 중복 방지
        if (messageType == MessageType.ENTER) {
            int enterCount = chatMessageRepository.countByBookClubAndMessageType(bookClub, MessageType.ENTER);
            boolean alreadyGenerated = discussionTopicRepository.existsByClub(bookClub);

            if (enterCount == 4 && !alreadyGenerated) {
                int bookId = bookClub.getBook().getBookId();
                List<String> userResponses = chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub)
                        .stream()
                        .filter(m -> m.getMessageType() == MessageType.SUBTOPIC)
                        .map(ChatMessage::getContent)
                        .collect(Collectors.toList());
                sendAiMainTopic(clubId, bookId, userResponses);
            }
        }

        // 수정2: SUBTOPIC 4개 완료되었을 때만 사용자 1 발언 안내
        if (messageType == MessageType.SUBTOPIC) {
            long subCount = chatMessageRepository.countByBookClubAndMessageType(bookClub, MessageType.SUBTOPIC);
            if (subCount == 4) {
                Optional<ChatMessage> first = chatMessageRepository.findFirstByBookClubAndMessageTypeOrderByCreatedTimeAsc(bookClub, MessageType.SUBTOPIC);
                first.ifPresent(firstMsg -> {
                    messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                            new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자", FIRST_SUBTOPIC_NOTICE, new Timestamp(System.currentTimeMillis())));

                    messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                            new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자", "안건: " + firstMsg.getContent(), new Timestamp(System.currentTimeMillis())));
                });
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

                String firstTopic = topics.get(0); // 수정3
                for (int i = 0; i < topics.size(); i++) {
                    discussionTopicRepository.save(DiscussionTopic.builder()
                            .club(bookClub)
                            .content(topics.get(i))
                            .createdTime(new Timestamp(System.currentTimeMillis()))
                            .version(i + 1)
                            .build());
                }

                messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                        new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자", GREETING_MESSAGE, new Timestamp(System.currentTimeMillis())));
                messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                        new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자", "대주제 1: " + firstTopic, new Timestamp(System.currentTimeMillis())));
            }
        } catch (Exception e) {
            System.err.println("AI 토픽 생성 실패: " + e.getMessage());
        }
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
}
