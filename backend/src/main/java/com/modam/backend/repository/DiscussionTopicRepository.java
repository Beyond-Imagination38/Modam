package com.modam.backend.repository;

import com.modam.backend.model.BookClub;
import com.modam.backend.model.DiscussionTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscussionTopicRepository extends JpaRepository<DiscussionTopic, Long> {
    List<DiscussionTopic> findByClub(BookClub club);

    List<DiscussionTopic> findByClubOrderByVersionAsc(BookClub club);

    boolean existsByClub(BookClub club);
}
