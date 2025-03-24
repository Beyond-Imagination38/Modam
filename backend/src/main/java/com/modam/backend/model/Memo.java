package com.modam.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "memo")
public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubId", nullable = false)
    private BookClub bookClub;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "createdTime", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "updatedTime")
    private LocalDateTime updatedTime;

    @PrePersist
    protected void onCreate() {
        this.createdTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedTime = LocalDateTime.now();
    }
}
