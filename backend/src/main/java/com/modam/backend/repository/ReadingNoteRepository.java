package com.modam.backend.repository;
//독후감 레포지토리

import com.modam.backend.model.ReadingNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
public interface ReadingNoteRepository extends JpaRepository<ReadingNote, Integer>{
    Optional<ReadingNote> findByBookClubClubIdAndUserUserId(int clubId, int userId);

    // [추가] 클럽의 모든 독후감 + 유저 정보 포함 조회
    @Query("SELECT r FROM ReadingNote r JOIN FETCH r.user WHERE r.bookClub.clubId = :clubId")
    List<ReadingNote> findByClubIdWithUser(@Param("clubId") int clubId);
}
