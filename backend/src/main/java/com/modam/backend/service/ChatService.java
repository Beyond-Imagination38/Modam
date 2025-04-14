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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final BookClubRepository bookClubRepository;
    private final UserRepository userRepository;
    private final DiscussionTopicRepository discussionTopicRepository;

    private static final String GREETING_MESSAGE = "안녕하세요 이번 모임은 책 1984에 대한 내용입니다. 첫번째 주제는 다음과 같습니다.";

    @SuppressWarnings("unchecked")
    @Transactional
    public ChatMessageDto saveChatMessage(int clubId, ChatMessageDto dto) {
        BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();
        User user = userRepository.findById(dto.getUserId()).orElseThrow();

        // 채팅 필터링 - 욕설 감지 (Flask 서버 호출 방식)
        boolean isBlocked = false;
        try {
            Map<String, String> filterRequest = new HashMap<>();
            filterRequest.put("text", dto.getContent());

            HttpHeaders filterHeaders = new HttpHeaders();
            filterHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> filterEntity = new HttpEntity<>(filterRequest, filterHeaders);

            ResponseEntity<Map> filterResponse = new RestTemplate()
                    .postForEntity("http://localhost:5000/ai/filter-chat", filterEntity, Map.class);

            if (filterResponse.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> body = filterResponse.getBody();
                isBlocked = Boolean.TRUE.equals(body.get("is_blocked"));
            }
        } catch (Exception e) {
            System.err.println("채팅 필터링 실패: " + e.getMessage());
        }

        // 차단된 메시지는 저장/브로드캐스트 하지 않음
        if (isBlocked) {

            System.out.println("🚫 차단된 메시지 감지됨: \"" + dto.getContent() + "\"");  //debug

            return new ChatMessageDto(
                    dto.getMessageType(),
                    clubId,
                    user.getUserId(),
                    user.getUserName(),
                    "[차단된 메시지입니다]", // FE 연결 전 테스트용
                    //dto.getContent(),         // FE 연결 후 실사용
                    new Timestamp(System.currentTimeMillis()),
                    null, // order
                    false, // shouldTriggerAiIntro
                    false, // shouldTriggerFirstDiscussion
                    true   // isBlocked!
            );
        }

        long userMsgCount = chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub).stream()
                .filter(m -> m.getUser().equals(user) && m.getMessageType() != MessageType.ENTER)
                .count();
        boolean isFirstMessage = userMsgCount == 0;

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

        boolean shouldTriggerAiIntro = false;
        boolean shouldTriggerFirstDiscussion = false;

        if (messageType == MessageType.ENTER) {
            long enterCount = chatMessageRepository.countByBookClubAndMessageType(bookClub, MessageType.ENTER);
            boolean alreadyGenerated = discussionTopicRepository.existsByClub(bookClub);
            shouldTriggerAiIntro = (enterCount == 4 && !alreadyGenerated);
        }

        if (messageType == MessageType.SUBTOPIC) {
            long subCount = chatMessageRepository.countByBookClubAndMessageType(bookClub, MessageType.SUBTOPIC);
            shouldTriggerFirstDiscussion = (subCount == 4);
        }

        return new ChatMessageDto(messageType, clubId, user.getUserId(), user.getUserName(),
                dto.getContent(), chatMessage.getCreatedTime(), order,
                shouldTriggerAiIntro, shouldTriggerFirstDiscussion, isBlocked);
    }

    @Transactional
    public void sendAiMainTopic(int clubId, int bookId) {
        BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();
        List<String> userResponses = chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub).stream()
                .filter(m -> m.getMessageType() == MessageType.SUBTOPIC)
                .map(ChatMessage::getContent)
                .collect(Collectors.toList());
        //더미 데이터
        if (userResponses.isEmpty()) {
            userResponses = List.of(
                    "나는 주인공 윈스턴의 외로움에 공감했어요.",
                    "전체주의 사회의 공포가 정말 생생하게 느껴졌습니다.",
                    "빅브라더의 감시는 현대 사회와도 닮은 것 같아요.",
                    "책의 결말이 충격적이었어요. 자유란 무엇인가 고민하게 되었습니다."
            );
        }

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("book_id", bookId);
        requestBody.put("user_responses", userResponses);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = new RestTemplate()
                    .postForEntity("http://localhost:5000/ai/generate-topics", request, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<String> topics = (List<String>) response.getBody().get("topics");
                for (int i = 0; i < topics.size(); i++) {
                    discussionTopicRepository.save(
                            DiscussionTopic.builder()
                                    .club(bookClub)
                                    .content(topics.get(i))
                                    .createdTime(new Timestamp(System.currentTimeMillis()))
                                    .version(i + 1)
                                    .build()
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("AI 토픽 생성 실패: " + e.getMessage());
        }
    }

    public Optional<String> getFirstDiscussionTopic(int clubId) {
        BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();
        return discussionTopicRepository.findFirstByClubOrderByVersionAsc(bookClub)
                .map(DiscussionTopic::getContent);
    }

    public Optional<String> getFirstUserSubtopic(int clubId) {
        BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();
        return chatMessageRepository.findByBookClubAndMessageTypeOrderByCreatedTimeAsc(bookClub, MessageType.SUBTOPIC)
                .stream().findFirst().map(ChatMessage::getContent);
    }

    @Transactional(readOnly = true)
    public List<ChatMessageDto> getChatHistory(int clubId) {
        BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();
        return chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub).stream()
                .map(m -> new ChatMessageDto(
                        m.getMessageType(),
                        clubId,
                        m.getUser().getUserId(),
                        m.getUser().getUserName(),
                        m.getContent(),
                        m.getCreatedTime()
                )).collect(Collectors.toList());
    }
}
