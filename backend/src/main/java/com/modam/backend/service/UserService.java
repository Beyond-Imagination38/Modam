package com.modam.backend.service;

import com.modam.backend.dto.UserDto;
import com.modam.backend.model.User;
import com.modam.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository user_repository;

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

    // 로그인
    public boolean login(UserDto user_dto) {
        Optional<User> user = user_repository.findByUserId(user_dto.getUser_id());
        return user.isPresent() && user.get().getPw().equals(user_dto.getPw());
    }

    // 회원 정보 조회
    public UserDto getUserbyuserid(String user_id) {
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
