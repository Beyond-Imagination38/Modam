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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chat_message_repository;
    private final BookClubRepository book_club_repository;
    private final UserRepository user_repository;

    @Transactional
    public ChatMessageDto saveChatMessage(int club_id, ChatMessageDto dto) {
        BookClub book_club = book_club_repository.findById(club_id)
                .orElseThrow(() -> new RuntimeException("BookClub not found with id: " + club_id));

        User user = user_repository.findById(dto.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUser_id()));

        ChatMessage chat_message = new ChatMessage(
                book_club,
                dto.getUser_id(),
                user.getUser_name(),
                dto.getContent()
        );

        chat_message_repository.save(chat_message);

        return new ChatMessageDto(
                club_id,
                dto.getUser_id(),
                user.getUser_name(),
                dto.getContent(),
                chat_message.getCreated_time()
        );
    }

    @Transactional(readOnly = true)
    public List<ChatMessageDto> getChatHistory(int club_id) {
        return chat_message_repository.findByBookClub_ClubId(club_id)
                .stream()
                .map(msg -> new ChatMessageDto(
                        club_id,
                        msg.getUser_id(),
                        msg.getUser_name(),
                        msg.getContent(),
                        msg.getCreated_time()
                ))
                .collect(Collectors.toList());
    }
}
