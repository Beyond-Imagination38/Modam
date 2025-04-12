package com.modam.backend.controller;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.service.ChatService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
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
}
