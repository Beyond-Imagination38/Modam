package com.modam.backend.service;


import com.modam.backend.dto.UserDto;
import com.modam.backend.model.User;
import com.modam.backend.repository.UserRepository;
import com.modam.backend.util.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository user_repository;
    private final JwtUtil jwtUtil;
    //private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository user_repository, JwtUtil jwtUtil) {

        this.user_repository = user_repository;
        this.jwtUtil = jwtUtil;
        //this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // 회원가입: 비밀번호 평문화, user_id는 DB 자동 생성
    public void register(UserDto user_dto) {
        User user = new User();
        user.setUserName(user_dto.getUserName());
        user.setEmail(user_dto.getEmail());
        user.setPw(user_dto.getPw());  // 평문 그대로 저장
        user.setProfileImage(user_dto.getProfileImage());
        user_repository.save(user);
    }

    // 로그인 -  이메일로 검색 후 비밀번호 검증 및 JWT 토큰 생성, 평문 비밀번호 비교
    public String login(String email, String pw) {
        Optional<User> userOpt = user_repository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPw().equals(pw)) {  // 평문 비교
                return jwtUtil.generateToken(String.valueOf(user.getUserId()));
            }
        }
        return null;
    }


    // 비밀번호 암호화 버전
/*    public String login(String email, String pw) {
        Optional<User> userOpt = user_repository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println("DB pw: '" + user.getPw() + "', 입력 pw: '" + pw + "'");
            if (passwordEncoder.matches(pw, user.getPw())) {   // 해시된 비밀번호와 비교 //add250521
                return jwtUtil.generateToken(String.valueOf(user.getUserId()));
            }

        }
        System.out.println("로그인 실패, 이메일: " + email);
        return null;
    }*/


    // 회원 정보 조회: (비밀번호는 반환하지 않음)
    public UserDto getUserbyuserid(Integer user_id) {
        User user = user_repository.findByUserId(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDto(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                null,
                user.getProfileImage()

        );
    }

    //마이페이지: 닉네임 업데이트
    @Transactional
    public void updateUserName(int userId, String newName) {
        User user = user_repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));
        user.setUserName(newName);
    }


}
