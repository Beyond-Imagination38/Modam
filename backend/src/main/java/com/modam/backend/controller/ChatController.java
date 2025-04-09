package com.modam.backend.controller;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chat_service;

    public ChatController(ChatService chat_service) {
        this.chat_service = chat_service;
    }

    @MessageMapping("/chat")  // 경로 단순화
    public void sendMessage(ChatMessageDto message) {
        // 클럽 ID는 메시지 내부에서 꺼냄
        chat_service.saveChatMessage(message.getClubId(), message);
    }
//    @MessageMapping("/chat")
//    @SendTo("/topic/chat/{club_id}")
//    public ChatMessageDto sendMessage(@PathVariable("club_id") int club_id, ChatMessageDto message) {
//        return chat_service.saveChatMessage(club_id, message);
//    }

    @GetMapping("/history/{club_id}")
    public List<ChatMessageDto> getChatHistory(@PathVariable("club_id") int club_id) {
        return chat_service.getChatHistory(club_id);
    }
}
