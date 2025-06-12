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
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.*;

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

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

    // clubId별 타이머 저장
    private final Map<Integer, ScheduledFuture<?>> timers = new ConcurrentHashMap<>();

    // clubId별 자유토론 모드 상태 저장
    private final Map<Integer, Boolean> freeDiscussionStatus = new ConcurrentHashMap<>();

    // 자유토론 모드 설정
    public void setFreeDiscussionMode(int clubId, boolean isActive) {
        freeDiscussionStatus.put(clubId, isActive);
    }

    public boolean isFreeDiscussionActive(int clubId) {
        return freeDiscussionStatus.getOrDefault(clubId, false);
    }

    //  FREE_DISCUSSION 메시지 수신 시 호출
    public void resetTimer(int clubId, int currentTopicVersion) {

        //자유토론 아니면 무시
        if (!isFreeDiscussionActive(clubId)) {
            System.out.println("⏹️ [Timer] 자유토론 상태 아님 → 타이머 시작 안 함: clubId=" + clubId);
            return;
        }

        ScheduledFuture<?> existing = timers.get(clubId);
        if (existing != null) {
            existing.cancel(false); // 기존 타이머 취소
        }

        Instant startTime = Instant.now();
        ScheduledFuture<?> newTimer = scheduler.schedule(() -> {
            handleTimeout(clubId, currentTopicVersion, startTime);
        }, 30, TimeUnit.SECONDS);

        timers.put(clubId, newTimer);
        System.out.println("🔁 [Timer] resetTimer: 클럽 ID " + clubId + ", 주제 버전 " + currentTopicVersion);
    }

    // 30초 후 타임아웃 체크
    private void handleTimeout(int clubId, int currentTopicVersion, Instant startTime) {
        BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();

        boolean noChat = chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub)
                .stream().noneMatch(m -> m.getCreatedTime().toInstant().isAfter(startTime));

        System.out.println("⏱️ [FreeDiscussion] no chat after 30 secs? → " + noChat);

        if (!noChat) return; // 채팅이 있으면 아무 일도 안 함

        if (currentTopicVersion < 3) {
            // Case 1: 1~2번째 주제
            messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                    new ChatMessageDto(MessageType.DISCUSSION_NOTICE, clubId, 0, "AI 진행자",
                            "시간이 완료되었습니다. 다음 주제로 넘어가겠습니다.", Timestamp.from(Instant.now())));
            setFreeDiscussionMode(clubId, false); // 자유토론 모드 종료

            // 다음 주제로 전환
            chatService.getDiscussionTopicByVersion(clubId, currentTopicVersion + 1)
                    .ifPresent(nextTopic -> {
                        messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                                new ChatMessageDto(MessageType.MAINTOPIC, clubId, 0, "AI 진행자",
                                        "대주제 " + (currentTopicVersion + 1) + ": " + nextTopic,
                                        Timestamp.from(Instant.now())));
                        subtopicDiscussionManager.startDiscussionFlow(clubId);
                    });

        }
        else {
            // Case 2: 마지막 주제
            messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                    new ChatMessageDto(MessageType.END_NOTICE, clubId, 0, "AI 진행자",
                            "시간이 완료되었습니다. 모임을 종료합니다.", Timestamp.from(Instant.now())));

            // 요약 전송을 ChatService로 위임
            chatService.sendMeetingSummary(clubId);

            //String summary = chatService.summarizeDiscussion(clubId);
            //messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                    //new ChatMessageDto(MessageType.SUMMARY, clubId, 0, "AI 진행자",
                            //"오늘 1984 독서모임 어떠셨나요?[모임 요약]\n" + summary, Timestamp.from(Instant.now())));
        }

        timers.remove(clubId); // 타이머 제거
    }
}
