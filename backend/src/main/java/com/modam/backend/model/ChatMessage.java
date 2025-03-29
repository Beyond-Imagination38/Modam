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
@Table(name = "chat_message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long message_id;

    @ManyToOne
    @JoinColumn(name = "club_id", nullable = false)
    private BookClub book_club;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "user_name")
    private String user_name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime created_time;

    @UpdateTimestamp
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updated_time;

    public ChatMessage(BookClub book_club, String user_id, String user_name, String content) {
        this.book_club = book_club;
        this.user_id = user_id;
        this.user_name = user_name;
        this.content = content;
    }
}
