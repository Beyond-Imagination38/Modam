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
    private Integer memoId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "club_id", nullable = false)
    private Integer clubId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime created_time;
    private LocalDateTime updated_time;

    private Boolean isFinalized = false;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.created_time = now;
        this.updated_time = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_time = LocalDateTime.now();
    }
}