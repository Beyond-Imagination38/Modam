package com.modam.backend.repository;

import com.modam.backend.model.BookClub;
import com.modam.backend.model.ChatMessage;
import com.modam.backend.model.MessageType;
import com.modam.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

    // 추가: SUBTOPIC 개수 세기
    int countByBookClubAndMessageType(BookClub bookClub, MessageType messageType);

    // 특정 독서모임의 모든 메시지 조회 (최신순)
    List<ChatMessage> findByBookClubOrderByCreatedTimeAsc(BookClub bookClub);

    // 특정 유저의 메시지 조회
    List<ChatMessage> findByUser(User user);


}
