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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chatmessage")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId") // `id` → `messageId`
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "clubId", nullable = false)
    private BookClub bookClub;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userName;  // 추가

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedTime;

    //@Column(nullable = false)
    //private LocalDateTime createdTime; // `timestamp` → `createdTime` 변경

    /*    // 새로운 생성자 (messageId 포함)
    public ChatMessage(Long messageId, BookClub bookClub, String userId, String userName, String content, LocalDateTime createdTime) {
        this.messageId = messageId;
        this.bookClub = bookClub;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.createdTime = createdTime;
    }*/

    // ID 없이 생성할 경우 자동으로 생성되도록 하는 생성자
    public ChatMessage(BookClub bookClub, String userId, String userName, String content, LocalDateTime createdTime) {
        this.bookClub = bookClub;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.createdTime = createdTime;
    }
}
