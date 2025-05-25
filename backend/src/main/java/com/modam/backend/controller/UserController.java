package com.modam.backend.controller;

import com.modam.backend.dto.UserDto;
import com.modam.backend.repository.UserRepository;
import com.modam.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService user_service;
    private final UserRepository user_repository;


    public UserController(UserService user_service, UserRepository user_repository) {

        this.user_service = user_service;
        this.user_repository = user_repository;
    }

    // 회원가입 - 유효성 검사 적용 //add250521
    @Operation(summary = "회원가입", description = "유저 정보를 받아 회원가입을 처리합니다.")
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody UserDto user_dto) {
        user_service.register(user_dto);
        return ResponseEntity.ok(Map.of("message","회원가입이 완료되었습니다."));
    }


    // 로그인 - 이메일, 비밀번호 받고 JWT 토큰 + userId 반환
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하고 JWT 토큰과 userId를 반환합니다.") //add250521
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String pw = loginRequest.get("pw");

        if (email == null || pw == null) {  // 간단한 입력 검증 //add250521
            return ResponseEntity.badRequest().body(Map.of("error", "이메일과 비밀번호를 모두 입력해주세요."));
        }

        String token = user_service.login(email, pw);

        if (token != null) {
            Optional<com.modam.backend.model.User> userOpt = user_repository.findByEmail(email);
            int userId = userOpt.get().getUserId();

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", userId);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "이메일 또는 비밀번호가 올바르지 않습니다."));
        }
    }


    // 로그아웃
    // 로그아웃 (JWT stateless 특성상 클라이언트 토큰 삭제 권장) //add250521
    @Operation(summary = "로그아웃", description = "서버에서는 별도 처리 없이 클라이언트에서 토큰 삭제로 로그아웃 처리합니다.") //add250521
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        return ResponseEntity.ok("로그아웃 완료");
    }

    // 회원 정보 조회( pw 제외 )
    @Operation(summary = "회원 정보 조회", description = "userId로 회원 정보를 조회합니다. 비밀번호는 반환하지 않습니다.") //add250521
    @GetMapping("/{user_id}")
    public ResponseEntity<UserDto> getUserinfo(@PathVariable("user_id") Integer user_id) {
        UserDto user_dto = user_service.getUserbyuserid(user_id);
        return ResponseEntity.ok(user_dto);

    }
}