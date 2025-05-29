package com.modam.backend.repository;

import com.modam.backend.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Integer> {

    List<Participant> findByUserUserId(int userId);

    List<Participant> findByUserUserIdAndStatus(int userId, String status);

    //참여자수 계산용 메서드
    int countByBookClubClubIdAndStatus(int clubId, String status);

    boolean existsByUserUserIdAndBookClubClubId(int userId, int clubId);

    @Query("SELECT p FROM Participant p JOIN FETCH p.bookClub WHERE p.user.userId = :userId")
    List<Participant> findWithBookClubByUserUserId(@Param("userId") int userId);


}