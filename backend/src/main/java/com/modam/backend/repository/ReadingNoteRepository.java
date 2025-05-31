package com.modam.backend.repository;
//독후감 레포지토리

import com.modam.backend.model.ReadingNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface ReadingNoteRepository extends JpaRepository<ReadingNote, Integer>{
    Optional<ReadingNote> findByBookClubClubIdAndUserUserId(int clubId, int userId);

}
