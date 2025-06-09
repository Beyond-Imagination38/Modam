package com.modam.backend.repository;

import com.modam.backend.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Integer> {
    Optional<Memo> findByClubIdAndUserId(Integer clubId, Integer userId);

    // [추가] 클럽의 모든 메모 + 유저 정보 포함 조회
    @Query("SELECT m FROM Memo m JOIN FETCH m.user WHERE m.clubId = :clubId")
    List<Memo> findByClubIdWithUser(@Param("clubId") int clubId);

}