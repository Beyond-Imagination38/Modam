package com.modam.backend.controller;

import com.modam.backend.dto.UserDto;
import com.modam.backend.service.UserService;
import com.modam.backend.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService user_service;
    private final UserRepository user_repository;


    public UserController(UserService user_service) {

        this.user_service = user_service;
        this.user_repository = user_repository;
    }

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserDto user_dto) {
        user_service.register(user_dto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // 로그인 - 이메일, 비밀번호 받고 JWT 토큰 + userId 반환
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String pw = loginRequest.get("pw");

        String token = user_service.login(email, pw);

        if (token != null) {
            Optional<com.modam.backend.model.User> userOpt = user_repository.findByEmail(email);
            int userId = userOpt.get().getUserId();

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", userId);

            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "이메일 또는 비밀번호가 올바르지 않습니다."));
        }
    }

/*    public ResponseEntity<String> loginUser(@RequestBody UserDto user_dto) {
        boolean success = user_service.login(user_dto);
        return success ? ResponseEntity.ok("로그인 성공") : ResponseEntity.badRequest().body("로그인 실패");
    }*/

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.ok("로그아웃 완료");
    }

    // 회원 정보 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getUserinfo(@PathVariable("user_id") Integer user_id) {
        UserDto user_dto = user_service.getUserbyuserid(user_id);
        return ResponseEntity.ok(user_dto);
    }
}
