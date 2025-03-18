package com.modam.backend.service;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.model.ChatMessage;
import com.modam.backend.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public ChatMessageDto saveMessage(ChatMessageDto dto) {
        ChatMessage chatMessage = new ChatMessage(null, dto.getRoomId(), dto.getSender(), dto.getContent(), dto.getTimestamp());
        chatMessageRepository.save(chatMessage);
        return dto;
    }

    public List<ChatMessageDto> getChatHistory(String roomId) {
        return chatMessageRepository.findByRoomId(roomId)
                .stream()
                .map(msg -> new ChatMessageDto(msg.getRoomId(), msg.getSender(), msg.getContent(), msg.getTimestamp()))
                .collect(Collectors.toList());
    }
}
