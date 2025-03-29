package com.modam.backend.repository;

import com.modam.backend.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByBookClub_ClubId(int club_id);  // clubId → club_id로 수정
}
