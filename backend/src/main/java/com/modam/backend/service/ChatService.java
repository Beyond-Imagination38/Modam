package com.modam.backend.service;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.model.BookClub;
import com.modam.backend.model.ChatMessage;
import com.modam.backend.repository.BookClubRepository;
import com.modam.backend.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final BookClubRepository bookClubRepository;

    public ChatService(ChatMessageRepository chatMessageRepository, BookClubRepository bookClubRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.bookClubRepository = bookClubRepository;
    }

    public ChatMessageDto saveMessage(int clubId, ChatMessageDto dto) {
        BookClub bookClub = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new IllegalArgumentException("BookClub not found with id: " + clubId));

        ChatMessage chatMessage = new ChatMessage(
                null, // ID 자동 생성
                bookClub, // clubId를 BookClub 객체로 매핑
                dto.getUserId(),
                dto.getContent(),
                LocalDateTime.now() // 현재 시간 저장
        );

        chatMessageRepository.save(chatMessage);
        return new ChatMessageDto(clubId, dto.getUserId(), dto.getContent(), chatMessage.getTimestamp());
    }

    public List<ChatMessageDto> getChatHistory(int clubId) {
        return chatMessageRepository.findByBookClub_ClubId(clubId)
                .stream()
                .map(msg -> new ChatMessageDto(
                        clubId, msg.getUserId(), msg.getContent(), msg.getTimestamp()
                ))
                .collect(Collectors.toList());
    }
}
