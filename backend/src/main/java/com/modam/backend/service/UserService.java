package com.modam.backend.service;

import com.modam.backend.dto.UserDto;
import com.modam.backend.model.User;
import com.modam.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public void register(UserDto userDto) {

        User user = new User();

        user.setUserId(userDto.getUserId());
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPw(userDto.getPw());
        user.setProfileImage(userDto.getProfileImage());
        //user.setCoins(userDto.getCoins());

        userRepository.save(user);
    }

    // 로그인
    public boolean login(UserDto userDto) {
        Optional<User> user = userRepository.findByUserId(userDto.getUserId());
        return user.isPresent() && user.get().getPw().equals(userDto.getPw());
    }


    // 회원 정보 조회
    public UserDto getUserByUserId(String userId) {
        User user = userRepository.findByUserId(userId)
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
