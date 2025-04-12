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

        Integer order = null;
        if (dto.getMessageType() == MessageType.SUBTOPIC) {
            order = chatMessageRepository.countByBookClubAndMessageType(bookClub, MessageType.SUBTOPIC) + 1;
            System.out.println("SUBTOPIC 감지됨. 현재 순서 = " + order);

        }

        ChatMessage chatMessage = ChatMessage.builder()
                .bookClub(bookClub)
                .user(user)
                .content(dto.getContent())
                .messageType(dto.getMessageType())
                .subtopicOrder(order)   //사용자 의견 순서 지정
                .build();

        chatMessageRepository.save(chatMessage);

        return new ChatMessageDto(
                dto.getMessageType(),
                clubId,
                user.getUserId(),
                user.getUserName(),
                dto.getContent(),
                chatMessage.getCreatedTime(),
                order   //dto로 전달
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
