package com.modam.backend.service;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.model.BookClub;
import com.modam.backend.model.ChatMessage;
import com.modam.backend.model.User;
import com.modam.backend.repository.BookClubRepository;
import com.modam.backend.repository.ChatMessageRepository;
import com.modam.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final BookClubRepository bookClubRepository;
    private final UserRepository userRepository;

    // 채팅 메시지 저장
    @Transactional
    public ChatMessageDto saveChatMessage(int clubId, ChatMessageDto dto) {
        BookClub bookClub = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));

        ChatMessage chatMessage = new ChatMessage(
                bookClub,
                dto.getUserId(),
                user.getUserName(), // userName 필드 추가 활용
                dto.getContent(),
                LocalDateTime.now() // createdTime으로 통일
        );

        chatMessageRepository.save(chatMessage);

        return new ChatMessageDto(
                clubId,
                dto.getUserId(),
                user.getUserName(),            //userName 추가
                dto.getContent(),
                chatMessage.getCreatedTime() // getTimestamp() → getCreatedTime()
        );
    }

    // 채팅 기록 조회
    @Transactional(readOnly = true)
    public List<ChatMessageDto> getChatHistory(int clubId) {
        return chatMessageRepository.findByBookClub_ClubId(clubId)
                .stream()
                .map(msg -> new ChatMessageDto(
                        clubId,
                        msg.getUserId(),
                        msg.getUserName(),      //userName 추가
                        msg.getContent(),
                        msg.getCreatedTime() // getTimestamp() → getCreatedTime()
                ))
                .collect(Collectors.toList());
    }
}
