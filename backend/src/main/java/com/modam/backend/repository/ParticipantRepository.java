package com.modam.backend.repository;

import com.modam.backend.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    List<Participant> findByUserUserId(int userId);

    List<Participant> findByUserUserIdAndStatus(int userId, String status);

    //참여자수 계산용 메서드
    int countByBookClubClubIdAndStatus(int clubId, String status);

    boolean existsByUserUserIdAndBookClubClubId(int userId, int clubId);



}