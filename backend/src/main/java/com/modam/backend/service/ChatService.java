package com.modam.backend.service;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.dto.SummaryCreateDto;
import com.modam.backend.model.*;
import com.modam.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final BookClubRepository bookClubRepository;
    private final UserRepository userRepository;
    private final DiscussionTopicRepository discussionTopicRepository;
    private final SummaryRepository summaryRepository;  //요약문

    private final SimpMessagingTemplate messagingTemplate; // 추가

    private static final String GREETING_MESSAGE = "안녕하세요 이번 모임은 책 1984에 대한 내용입니다. 첫번째 주제는 다음과 같습니다.";


    @Value("${ai.server.url}")
    private String aiServerUrl;

    // soo:요약문+상태 - 요약문 저장 및 모임 상태 변경 메서드 추가

    //요약문+상태:수정버전
    @Transactional
    public void saveSummaryAndCompleteClub(int clubId, List<SummaryCreateDto> summaryList) {
        BookClub bookClub = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));

        // 기존 summary 삭제 (덮어쓰기 방지)
        summaryRepository.deleteByBookClubClubId(clubId);

        for (SummaryCreateDto dto : summaryList) {
            Summary summary = new Summary();
            summary.setBookClub(bookClub);
            summary.setTopicNumber(dto.getTopicNumber());
            summary.setTopic(dto.getTopic());
            summary.setContent(dto.getContent());

            summaryRepository.save(summary);
        }

        bookClub.setStatus("COMPLETED");
        bookClubRepository.save(bookClub);
    }




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

            // soo:0618 URL 하드코딩 제거
            ResponseEntity<Map> filterResponse = new RestTemplate()
                    .postForEntity(aiServerUrl + "/ai/filter-chat", filterEntity, Map.class);

            if (filterResponse.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> body = filterResponse.getBody();
                isBlocked = Boolean.TRUE.equals(body.get("is_blocked"));
            }
        } catch (Exception e) {
            System.err.println("채팅 필터링 실패: " + e.getMessage());
        }

        // 차단된 메시지는 저장/브로드캐스트 하지 않음
        if (isBlocked) {

            System.out.println(" 차단된 메시지 감지됨: \"" + dto.getContent() + "\"");  //debug

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
        } else if (dto.getMessageType() == MessageType.FREE_DISCUSSION) {
            messageType = MessageType.FREE_DISCUSSION;
        }
        else {
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
                    .postForEntity(aiServerUrl + "/ai/generate-topics", request, Map.class);
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

    //demo02: 요약
    public void sendMeetingSummary(int clubId) {
        String summary = summarizeDiscussion(clubId);

        ChatMessageDto summaryMessage = new ChatMessageDto(
                MessageType.SUMMARY,
                clubId,
                0,
                "AI 진행자",
                summary,
                new Timestamp(System.currentTimeMillis())
        );

        messagingTemplate.convertAndSend("/topic/chat/" + clubId, summaryMessage);


    }


    //첫번째 유저 말 -> topic
    public Optional<String> getFirstDiscussionTopic(int clubId) {
        BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();
        return discussionTopicRepository.findFirstByClubOrderByVersionAsc(bookClub)
                .map(DiscussionTopic::getContent);
    }

    //n번째 유저 -> topic demo02
    public Optional<String> getDiscussionTopicByVersion(int clubId, int version) {
        BookClub club = bookClubRepository.findById(clubId).orElseThrow();
        return discussionTopicRepository.findByClubOrderByVersionAsc(club).stream()
                .filter(topic -> topic.getVersion() == version)
                .map(DiscussionTopic::getContent)
                .findFirst();
    }



    // 첫번째 유저의 말 -> subtopic
    public Optional<String> getFirstUserSubtopic(int clubId) {
        BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();
        return chatMessageRepository.findByBookClubAndMessageTypeOrderByCreatedTimeAsc(bookClub, MessageType.SUBTOPIC)
                .stream().findFirst().map(ChatMessage::getContent);
    }

    // n번째 user의 채팅 -> subtopic //demo02
    public Optional<String> getNthUserSubtopic(int clubId, int order) {
        BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();

        return chatMessageRepository
                .findByBookClubAndMessageTypeOrderByCreatedTimeAsc(bookClub, MessageType.SUBTOPIC)
                .stream()
                .filter(msg -> msg.getSubtopicOrder() != null && msg.getSubtopicOrder() == order)
                .map(ChatMessage::getContent)
                .findFirst();
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

    //demo02 :요약하기
    @Transactional(readOnly = true)
    public String summarizeDiscussion(int clubId) {
        BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();

        // 1. 대주제 목록 조회
        List<DiscussionTopic> topics = discussionTopicRepository.findByClubOrderByVersionAsc(bookClub);
        List<String> topicContents = topics.stream()
                .map(DiscussionTopic::getContent)
                .collect(Collectors.toList());

        // 2. 각 대주제별 참가자 응답 수집
        List<List<String>> allResponses = topics.stream()
                .map(topic -> {
                    int version = topic.getVersion();
                    return chatMessageRepository.findByBookClubAndMessageTypeOrderByCreatedTimeAsc(bookClub, MessageType.SUBTOPIC).stream()
                            .filter(msg -> msg.getSubtopicOrder() != null)
                            .skip((long) (version - 1) * 4)
                            .limit(4)
                            .map(ChatMessage::getContent)
                            .toList();
                })
                .toList();

        // 3. Flask 서버 호출
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("topics", topicContents);
            requestBody.put("all_responses", allResponses);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // soo: 0618 URL 하드코딩 제거
            String url = aiServerUrl + "/ai/summarize";
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);


            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<Map<String, String>> summaries = (List<Map<String, String>>) response.getBody().get("summaries");

                StringBuilder result = new StringBuilder();
                result.append("오늘 『1984』 독서 모임 어떠셨나요?\n")
                        .append("오늘 토의 내용을 요약해드릴게요.\n\n");

                for (int i = 0; i < summaries.size(); i++) {
                    Map<String, String> s = summaries.get(i);
                    String topicTitle = s.get("topic");
                    String summaryText = s.get("summary");

                    result.append("주제 ").append(i + 1).append(": ").append("\"").append(topicTitle).append("\"\n\n");
                    result.append(summaryText).append("\n\n");
                }

                return result.toString();
            } else {


                return "[AI 요약 실패] 서버 응답 오류";
            }

        } catch (Exception e) {

            //log.error("AI 요약 실패", e);
            return "[AI 요약 실패] " + e.getMessage();
        }
    }

    @Transactional(readOnly = true)
    public int getCurrentTopicVersion(int clubId) {
        BookClub club = bookClubRepository.findById(clubId).orElseThrow();
        return discussionTopicRepository.findFirstByClubOrderByVersionDesc(club)
                .map(DiscussionTopic::getVersion)
                .orElse(1);
    }

}
