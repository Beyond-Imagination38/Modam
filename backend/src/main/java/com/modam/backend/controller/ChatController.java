package com.modam.backend.controller;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.model.BookClub;
import com.modam.backend.service.BookClubService;
import com.modam.backend.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final BookClubService bookClubService;

    private final RestTemplate restTemplate = new RestTemplate(); //soo: AI 서버 호출용


    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate, BookClubService bookClubService) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
        this.bookClubService = bookClubService;
    }



    @MessageMapping("/chat/{clubId}")
    public void sendMessage(@DestinationVariable int clubId, ChatMessageDto message) {

        System.out.println("수신된 clubId: " + clubId); // debugging
        System.out.println("받은 메시지 DTO: " + message); // debugging

        ChatMessageDto saved = chatService.saveChatMessage(clubId, message);

        System.out.println("DB 저장 후 브로드캐스트 직전 DTO: " + saved); // debugging

        // 클럽 ID 기반 브로드캐스트
        messagingTemplate.convertAndSend("/topic/chat/" + clubId, saved);

        System.out.println("브로드캐스트 완료: /topic/chat/" + clubId); // debugging
    }

    @GetMapping("/history/{club_id}")
    public List<ChatMessageDto> getChatHistory(@PathVariable("club_id") int club_id) {
        return chatService.getChatHistory(club_id);
    }

    //4-1. 발제문 생성
    @PostMapping("/start/{clubId}")
    public ResponseEntity<String> startChatAndSendTopics(@PathVariable int clubId) {
        try {
            BookClub bookClub = bookClubService.getBookClub(clubId);
            int bookId = bookClub.getBook().getBookId();

            List<String> userResponses = List.of(
                    "나는 주인공 윈스턴의 외로움에 공감했어요.",
                    "전체주의 사회의 공포가 정말 생생하게 느껴졌습니다.",
                    "빅브라더의 감시는 현대 사회와도 닮은 것 같아요."
            );

            // ChatService로 위임 (sendAiMainTopic 내부에 Flask 호출 + 메시지 전송 포함)
            chatService.sendAiMainTopic(clubId, bookId, userResponses);

            return ResponseEntity.ok("발제문 전송 완료");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생: " + e.getMessage());
        }
    }

}
