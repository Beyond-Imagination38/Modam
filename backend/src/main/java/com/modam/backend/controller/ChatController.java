package com.modam.backend.controller;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.dto.SummaryCreateDto;
import com.modam.backend.handler.FreeDiscussionManager;
import com.modam.backend.handler.SubtopicDiscussionManager;
import com.modam.backend.model.BookClub;
import com.modam.backend.model.MessageType;
import com.modam.backend.service.BookClubService;
import com.modam.backend.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final BookClubService bookClubService;
    private final SubtopicDiscussionManager subtopicDiscussionManager;

    private final FreeDiscussionManager freeDiscussionManager;//test02용 demo02



    public ChatController
            (ChatService chatService, SimpMessagingTemplate messagingTemplate, BookClubService bookClubService, SubtopicDiscussionManager subtopicDiscussionManager, FreeDiscussionManager freeDiscussionManager) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
        this.bookClubService = bookClubService;
        this.subtopicDiscussionManager = subtopicDiscussionManager;

        this.freeDiscussionManager = freeDiscussionManager;//test02용 demo02
    }

    // soo:요약문+상태 - 요약문 저장 및 상태 변경 API 엔드포인트 추가
/*    @PostMapping("/{clubId}/summary")
    public ResponseEntity<String> saveSummary(@PathVariable int clubId, @RequestBody Map<String, String> request) {
        String summary = request.get("summary");
        if (summary == null || summary.isBlank()) {
            return ResponseEntity.badRequest().body("요약문이 필요합니다.");
        }
        chatService.saveSummaryAndCompleteClub(clubId, summary);
        return ResponseEntity.ok("요약문 저장 및 상태 변경 완료");
    }*/
    //요약문: 수정
    @PostMapping("/{clubId}/summary")
    @Operation(summary = "요약 저장", description = "주제별 요약을 저장하고, 모임 상태를 COMPLETED로 변경합니다.")
    public ResponseEntity<String> saveSummary(
            @PathVariable int clubId,
            @RequestBody List<SummaryCreateDto> summaryList) {

        if (summaryList == null || summaryList.isEmpty()) {
            return ResponseEntity.badRequest().body("요약 데이터가 필요합니다.");
        }

        chatService.saveSummaryAndCompleteClub(clubId, summaryList);
        return ResponseEntity.ok("요약 저장 및 모임 상태 변경 완료");
    }


    //test
    @GetMapping("/test/summary/{clubId}")
    public void testSummary(@PathVariable int clubId) {
        chatService.sendMeetingSummary(clubId);
    }

    @MessageMapping("/chat/{clubId}")
    public void sendMessage(@DestinationVariable int clubId, ChatMessageDto message) {
        ChatMessageDto saved = chatService.saveChatMessage(clubId, message);

        messagingTemplate.convertAndSend("/topic/chat/" + clubId, saved);

        if (saved.getMessageType() == MessageType.FREE_DISCUSSION) {
            int currentTopicVersion = chatService.getCurrentTopicVersion(clubId);
            freeDiscussionManager.resetTimer(clubId, currentTopicVersion);
        }

        if (saved.isShouldTriggerAiIntro()) {
            BookClub bookClub = bookClubService.getBookClub(clubId);
            int bookId = bookClub.getBook().getBookId();

            // AI 토픽 생성
            chatService.sendAiMainTopic(clubId, bookId);

            // AI 인삿말 및 대주제
            messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                    new ChatMessageDto(MessageType.DISCUSSION_NOTICE, clubId, 0, "AI 진행자",
                            "안녕하세요 이번 모임은 책 1984에 대한 내용입니다. 첫번째 주제는 다음과 같습니다.",
                            new Timestamp(System.currentTimeMillis())));

            chatService.getFirstDiscussionTopic(clubId).ifPresent(topic -> {
                messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                        new ChatMessageDto(MessageType.MAINTOPIC, clubId, 0, "AI 진행자",
                                "대주제 1: " + topic, new Timestamp(System.currentTimeMillis())));
            });
        }

        // 자동 토론 흐름 시작: demo02
        //soo: 0613 발제문 3번 반복
        if (saved.isShouldTriggerFirstDiscussion()) {
            int currentTopicVersion = chatService.getCurrentTopicVersion(clubId);

            // 대주제 출력
            chatService.getDiscussionTopicByVersion(clubId, currentTopicVersion).ifPresent(topic -> {
                messagingTemplate.convertAndSend("/topic/chat/" + clubId,
                        new ChatMessageDto(MessageType.MAINTOPIC, clubId, 0, "AI 진행자",
                                "대주제 " + currentTopicVersion + ": " + topic,
                                new Timestamp(System.currentTimeMillis())));
            });

            // 자동 소주제 토론 시작
            subtopicDiscussionManager.startDiscussionFlow(clubId);
        }


    }

    @GetMapping("/history/{club_id}")
    public List<ChatMessageDto> getChatHistory(@PathVariable("club_id") int club_id) {
        return chatService.getChatHistory(club_id);
    }




}
