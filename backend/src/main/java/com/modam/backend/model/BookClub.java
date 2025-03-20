package com.modam.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "BookClub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clubId")
    private int clubId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId", nullable = false)
    private User host;

    @Column(name = "bookId", nullable = false) // **변경된 부분**
    private String bookId;

    @Column(name = "meetingDate", nullable = false)
    private LocalDateTime meetingDate;

    @Column(name = "status", nullable = false, columnDefinition = "ENUM('PENDING', 'ONGOING', 'COMPLETED') DEFAULT 'PENDING'")
    private String status;

    @Column(name = "likes", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int likes;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "searchIndex", columnDefinition = "TEXT")
    private String searchIndex;

    @Column(name = "createdTime", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "updatedTime")
    private LocalDateTime updatedTime;

    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = LocalDateTime.now();
    }
}
