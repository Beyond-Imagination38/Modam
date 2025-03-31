package com.modam.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "book_club")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private int clubId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    @Column(name = "book_id", nullable = false)
    private String book_id;

    @Column(name = "meeting_date", nullable = false)
    private LocalDateTime meeting_date;

    @Column(name = "status", nullable = false, columnDefinition = "ENUM('PENDING', 'ONGOING', 'COMPLETED') DEFAULT 'PENDING'")
    private String status;

    @Column(name = "likes", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int likes;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "search_index", columnDefinition = "TEXT")
    private String search_index;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime created_time;

    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updated_time;
}
