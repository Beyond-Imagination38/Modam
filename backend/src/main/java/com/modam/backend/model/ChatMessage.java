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
    @Column(name = "messageId")
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "clubId", nullable = false)
    private BookClub bookClub;

    @Column(name = "userId")
    private String userId;

    @Column(name = "userName")
    private String userName;  // 추가

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "createdTime", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "updatedTime", nullable = false)
    private LocalDateTime updatedTime;

    public ChatMessage(BookClub bookClub, String userId, String userName, String content) {
        this.bookClub = bookClub;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
    }

}
