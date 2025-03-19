package com.modam.backend.controller;

import com.modam.backend.dto.UserDto;
import com.modam.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userService.register(userDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDto userDto) {
        boolean success = userService.login(userDto);
        return success ? ResponseEntity.ok("로그인 성공") : ResponseEntity.badRequest().body("로그인 실패");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.ok("로그아웃 완료");
    }

    // 회원 정보 조회 (userId 기반)
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable String userId) {
        UserDto userDto = userService.getUserByUserId(userId);  // userId로 조회하도록 변경
        return ResponseEntity.ok(userDto);
    }
}
