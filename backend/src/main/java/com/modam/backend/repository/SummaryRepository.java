package com.modam.backend.repository;

import com.modam.backend.model.Summary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SummaryRepository extends JpaRepository<Summary, Integer> {
    List<Summary> findByBookClubClubIdOrderByTopicNumberAsc(int clubId);

    void deleteByBookClubClubId(int clubId);

}
