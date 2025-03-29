package com.modam.backend.controller;

import com.modam.backend.dto.UserDto;
import com.modam.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService user_service;

    public UserController(UserService user_service) {
        this.user_service = user_service;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserDto user_dto) {
        user_service.register(user_dto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDto user_dto) {
        boolean success = user_service.login(user_dto);
        return success ? ResponseEntity.ok("로그인 성공") : ResponseEntity.badRequest().body("로그인 실패");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.ok("로그아웃 완료");
    }

    // 회원 정보 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("user_id") String user_id) {
        UserDto user_dto = user_service.getUserByUserId(user_id);
        return ResponseEntity.ok(user_dto);
    }
}
