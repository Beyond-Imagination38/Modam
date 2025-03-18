package com.modam.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "password", length = 255, nullable = false)
    private String password;  // pw → password 변경 (더 직관적)

    @Column(name = "profileImage", columnDefinition = "TEXT")
    private String profileImage;

    @Column(name = "coins", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int coins;
}
