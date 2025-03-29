package com.modam.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "memo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long memo_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = false)
    private BookClub book_club;

    @Column(name = "user_id", nullable = false)
    private String user_id;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime created_time;

    @Column(name = "updated_time")
    private LocalDateTime updated_time;

    @PrePersist
    protected void onCreate() {
        this.created_time = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_time = LocalDateTime.now();
    }
}
