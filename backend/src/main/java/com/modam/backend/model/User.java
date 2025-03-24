package com.modam.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "User")  // 테이블명
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "userId", length = 50, nullable = false, unique = true)
    private String userId;

    @Column(name = "userName", length = 100, nullable = false)
    private String userName;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "pw", length = 255, nullable = false)
    private String pw;

    @Column(name = "profileImage", columnDefinition = "TEXT")
    private String profileImage;

    @Column(name = "coins", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int coins;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedTime;
}
