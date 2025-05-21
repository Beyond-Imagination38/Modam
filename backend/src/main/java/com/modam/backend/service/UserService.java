package com.modam.backend.service;


import com.modam.backend.model.User;
import com.modam.backend.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository user_repository;

    private final String SECRET_KEY = "your-secret-key"; // 반드시 안전하게 관리

    public UserService(UserRepository user_repository) {

        this.user_repository = user_repository;
    }

    // 회원가입
    public void register(UserDto user_dto) {
        User user = new User();
        user.setUserId(user_dto.getUser_id());
        user.setUserName(user_dto.getUser_name());
        user.setEmail(user_dto.getEmail());
        user.setPw(user_dto.getPw());
        user.setProfileImage(user_dto.getProfile_image());
        user_repository.save(user);
    }

    // 로그인 -  이메일로 검색 후 비밀번호 검증 및 JWT 토큰 생성

    /**
     * 로그인: 이메일과 비밀번호로 사용자 확인 후 JWT 토큰 발급
     *
     * @param email 로그인 이메일
     * @param pw 비밀번호
     * @return JWT 토큰 (로그인 실패 시 null)
     */
    public String login(String email, String pw) {
        Optional<User> userOpt = user_repository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPw().equals(pw)) {
                // JWT 토큰 생성
                String token = Jwts.builder()
                        .setSubject(String.valueOf(user.getUserId()))
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1일 토큰 유효기간
                        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                        .compact();
                return token;
            }
        }
        return null;
    }


/*    public boolean login(UserDto user_dto) {
        Optional<User> user = user_repository.findByUserId(user_dto.getUser_id());
        return user.isPresent() && user.get().getPw().equals(user_dto.getPw());
    }*/

    // 회원 정보 조회
    public UserDto getUserbyuserid(Integer user_id) {
        User user = user_repository.findByUserId(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDto(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.getPw(),
                user.getProfileImage(),
                user.getCoins()
        );
    }
}
