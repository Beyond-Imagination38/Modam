package com.modam.backend.service;

import com.modam.backend.dto.ChatMessageDto;
import com.modam.backend.model.BookClub;
import com.modam.backend.model.ChatMessage;
import com.modam.backend.model.MessageType;
import com.modam.backend.model.User;
import com.modam.backend.repository.BookClubRepository;
import com.modam.backend.repository.ChatMessageRepository;
import com.modam.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final BookClubRepository bookClubRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatMessageDto saveChatMessage(int clubId, ChatMessageDto dto) {

        System.out.println("메시지 저장 요청: " + dto);

        BookClub bookClub = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));

        // 유저가 이 club에서 처음 메시지 보내는지 확인
        boolean isFirstMessage = chatMessageRepository
                .findByBookClubOrderByCreatedTimeAsc(bookClub)
                .stream()
                .noneMatch(m -> m.getUser().equals(user));

        MessageType messageType;
        Integer order = null;

        if (isFirstMessage) {
            messageType = MessageType.SUBTOPIC;
            order = chatMessageRepository.countByBookClubAndMessageType(bookClub, MessageType.SUBTOPIC) + 1;
            System.out.println("처음 메시지 → SUBTOPIC, 순서: " + order);
        } else {
            messageType = MessageType.DISCUSSION;
        }

        ChatMessage chatMessage = ChatMessage.builder()
                .bookClub(bookClub)
                .user(user)
                .content(dto.getContent())
                .messageType(messageType) // 클라이언트 input 무시
                .subtopicOrder(order)
                .build();

        chatMessageRepository.save(chatMessage);

        return new ChatMessageDto(
                messageType,
                clubId,
                user.getUserId(),
                user.getUserName(),
                dto.getContent(),
                chatMessage.getCreatedTime(),
                order
        );
    }

    @Transactional(readOnly = true)
    public List<ChatMessageDto> getChatHistory(int clubId) {
        BookClub bookClub = bookClubRepository.findById(clubId)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + clubId));

        return chatMessageRepository.findByBookClubOrderByCreatedTimeAsc(bookClub)
                .stream()
                .map(msg -> new ChatMessageDto(
                        msg.getMessageType(),
                        clubId,
                        msg.getUser().getUserId(),
                        msg.getUser().getUserName(),
                        msg.getContent(),
                        msg.getCreatedTime()
                ))
                .collect(Collectors.toList());
    }

}
