package com.modam.backend.handler;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.model.MessageType;
import com.modam.backend.repository.BookClubRepository;
import com.modam.backend.repository.DiscussionTopicRepository;
import com.modam.backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SubtopicDiscussionManager {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final FreeDiscussionManager freeDiscussionManager;
    private final DiscussionTopicRepository discussionTopicRepository;
    private final BookClubRepository bookClubRepository;



    @Async
    public void startDiscussionFlow(int clubId) {
        for (int i = 1; i <= 4; i++) {
            int order = i;

            chatService.getNthUserSubtopic(clubId, order).ifPresent(content -> {
                // 의견 출력
                messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                        new ChatMessageDto(
                                MessageType.SUBTOPIC,
                                clubId,
                                0,
                                "AI 진행자",
                                order + "번째 안건에 대하여 이야기 해봅시다.\n" + "안건" + order + ": "+ content,
                                new Timestamp(System.currentTimeMillis())
                        )
                );

                // 4분 기다림
                try {
                    TimeUnit.MINUTES.sleep(1);  //임시로 1분으로 지정
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // 4분 후 안내 메시지 출력
                messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                        new ChatMessageDto(
                                MessageType.DISCUSSION_NOTICE,
                                clubId,
                                0,
                                "AI 진행자",
                                "4분이 지났습니다. 다음 안건으로 넘어가겠습니다.",
                                new Timestamp(System.currentTimeMillis())
                        )
                );
            });
        }

        // 4개 의견 끝나면 자유 토론 유도
        messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                new ChatMessageDto(
                        MessageType.FREE_DISCUSSION_NOTICE,
                        clubId,
                        0,
                        "AI 진행자",
                        "4개의 의견을 모두 살펴봤습니다. 더 이야기하고 싶은 주제가 있다면 자유롭게 이야기해주세요!",
                        new Timestamp(System.currentTimeMillis())
                )
        );

        freeDiscussionManager.setFreeDiscussionMode(clubId, true);


        int currentTopicVersion = chatService.getCurrentTopicVersion(clubId);
        freeDiscussionManager.resetTimer(clubId, currentTopicVersion);

        // 안내 메시지 전송 직후 시간 기록
        //Instant discussionStart = Instant.now();

        // version 확인
        //BookClub bookClub = bookClubRepository.findById(clubId).orElseThrow();
        //int currentTopicVersion = discussionTopicRepository.findFirstByClubOrderByVersionDesc(bookClub)
        //        .map(DiscussionTopic::getVersion)
        //        .orElse(1);

        // 정확한 시간 전달
        //freeDiscussionManager.monitorInactivityAndSwitchTopic(clubId, currentTopicVersion, discussionStart);



    }

}
