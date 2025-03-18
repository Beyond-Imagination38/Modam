package com.modam.backend.controller;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.service.ChatService;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chat/{roomId}")
    public ChatMessageDto sendMessage(ChatMessageDto message) {
        return chatService.saveMessage(message);
    }

    @GetMapping("/history/{roomId}")
    public List<ChatMessageDto> getChatHistory(@PathVariable String roomId) {
        return chatService.getChatHistory(roomId);
    }
}
