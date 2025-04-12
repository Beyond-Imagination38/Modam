package com.modam.backend.controller;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.model.MessageType;
import com.modam.backend.model.BookClub;
import com.modam.backend.service.BookClubService;
import com.modam.backend.service.ChatService;

import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;


@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final BookClubService bookClubService;

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

    //발제문 생성
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

            String flaskUrl = "http://localhost:5000/ai/generate-topics";

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("book_id", bookId);
            requestBody.put("user_responses", userResponses);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(flaskUrl, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<String> topics = (List<String>) response.getBody().get("topics");
                for (String topic : topics) {
                    ChatMessageDto topicMessage = new ChatMessageDto(
                            MessageType.TOPIC_START,
                            clubId,
                            0,
                            "사회자",
                            topic,
                            new Timestamp(System.currentTimeMillis())
                    );
                    messagingTemplate.convertAndSend("/topic/chat/" + clubId, topicMessage);
                }
                return ResponseEntity.ok("발제문 전송 완료");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Flask 호출 실패");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 발생: " + e.getMessage());
        }
    }

}
