package com.modam.backend.repository;

import com.modam.backend.model.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Integer> {
    Optional<Memo> findByClubIdAndUserId(Integer clubId, Integer userId);
}