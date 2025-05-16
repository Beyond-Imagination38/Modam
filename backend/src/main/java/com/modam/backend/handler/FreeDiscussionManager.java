package com.modam.backend.handler;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.model.BookClub;
import com.modam.backend.model.MessageType;
import com.modam.backend.repository.BookClubRepository;
import com.modam.backend.repository.ChatMessageRepository;
import com.modam.backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

//demo02
@Component
@RequiredArgsConstructor
public class FreeDiscussionManager {

    private final ChatMessageRepository chatMessageRepository;
    private final BookClubRepository bookClubRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;


    @Lazy
    @Autowired
    private SubtopicDiscussionManager subtopicDiscussionManager;

    @Async
    public void monitorInactivityAndSwitchTopic(int clubId, int currentTopicVersion) {
        try {
            Instant start = Instant.now();
            System.out.println("🧭 자유토론 감시 시작: clubId = " + clubId + ", version = " + currentTopicVersion);

            BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();

            TimeUnit.SECONDS.sleep(30);

            boolean noChatIn30s = chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub)
                    .stream()
                    .noneMatch(m -> m.getCreatedTime().toInstant().isAfter(start));

            System.out.println("🕒 30초 후 채팅 없음? → " + noChatIn30s);

            if (noChatIn30s) {
                messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                        new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자",
                                "30초 동안 대화가 없어 다음 주제로 넘어가시겠습니까?", Timestamp.from(Instant.now())));

                TimeUnit.SECONDS.sleep(10);

                Instant confirmTime = Instant.now();

                boolean stillNoChat = chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub)
                        .stream()
                        .noneMatch(m -> m.getCreatedTime().toInstant().isAfter(confirmTime));

                System.out.println("🕙 추가 10초 후에도 없음? → " + stillNoChat);

                if (stillNoChat) {
                    messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                            new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자",
                                    "10초가 지났습니다. 다음 주제로 넘어갑니다.", Timestamp.from(Instant.now())));

                    if (currentTopicVersion >= 3) {
                        messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                                new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자",
                                        "모임이 종료되었습니다. 참여해주셔서 감사합니다!", Timestamp.from(Instant.now())));

                        String summary = chatService.summarizeDiscussion(clubId);
                        messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                                new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자",
                                        "[모임 요약]\n" + summary, Timestamp.from(Instant.now())));
                    } else {
                        chatService.getDiscussionTopicByVersion(clubId, currentTopicVersion + 1).ifPresent(nextTopic -> {
                            messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                                    new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자",
                                            "대주제 " + (currentTopicVersion + 1) + ": " + nextTopic,
                                            Timestamp.from(Instant.now())));

                            subtopicDiscussionManager.startDiscussionFlow(clubId);
                        });
                    }
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


/*
    @Async
    public void monitorInactivityAndSwitchTopic(int clubId, int currentTopicVersion) {
        try {
            Instant start = Instant.now();

            // 1. BookClub 조회 (올바르게 가져오기) //demo02
            BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();

            // 2. 30초 대기
            TimeUnit.SECONDS.sleep(30);

            // 3. 30초 내 채팅 없었는지 확인
            boolean noChatIn30s = chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub)
                    .stream()
                    .noneMatch(m -> m.getCreatedTime().toInstant().isAfter(start));

            if (noChatIn30s) {
                messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                        new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자",
                                "30초 동안 대화가 없어 다음 주제로 넘어가시겠습니까?", Timestamp.from(Instant.now())));

                // 4. 추가로 10초 대기
                TimeUnit.SECONDS.sleep(10);

                Instant confirmTime = Instant.now();

                // 5. 이후에도 채팅 없었는지 확인
                boolean stillNoChat = chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub)
                        .stream()
                        .noneMatch(m -> m.getCreatedTime().toInstant().isAfter(confirmTime));


                if (stillNoChat) {
                    // 6. 안내 메시지 전송
                    messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                            new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자",
                                    "10초가 지났습니다. 다음 주제로 넘어갑니다.", Timestamp.from(Instant.now())));

                    // 7. 다음 대주제 version 가져오기 (version + 1)
                    if (currentTopicVersion >= 3) {
                        // 마지막 주제까지 완료된 경우
                        messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                                new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자",
                                        "모임이 종료되었습니다. 참여해주셔서 감사합니다!", Timestamp.from(Instant.now()))
                        );

                        // 모임 요약 출력
                        String summary = chatService.summarizeDiscussion(clubId);  // Step 2에서 구현할 것

                        messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                                new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자",
                                        "[모임 요약]\n" + summary, Timestamp.from(Instant.now()))
                        );

                    } else {
                        // 다음 대주제가 있는 경우
                        chatService.getDiscussionTopicByVersion(clubId, currentTopicVersion + 1).ifPresent(nextTopic -> {
                            messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                                    new ChatMessageDto(MessageType.TOPIC_START, clubId, 0, "AI 진행자",
                                            "대주제 " + (currentTopicVersion + 1) + ": " + nextTopic,
                                            Timestamp.from(Instant.now()))
                            );

                            subtopicDiscussionManager.startDiscussionFlow(clubId);
                        });
                    }


                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }*/
}
