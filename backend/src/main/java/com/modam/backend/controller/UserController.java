package com.modam.backend.controller;

import com.modam.backend.dto.PasswordUpdateDto;
import com.modam.backend.dto.UserDto;
import com.modam.backend.dto.UserNameUpdateDto;
import com.modam.backend.repository.UserRepository;
import com.modam.backend.service.UserService;
import com.modam.backend.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService user_service;
    private final UserRepository user_repository;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;


    public UserController(UserService user_service, UserRepository user_repository, JwtUtil jwtUtil, RedisTemplate<String, String> redisTemplate) {

        this.user_service = user_service;
        this.user_repository = user_repository;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    // 회원가입 - 유효성 검사 적용 //add250521
    @Operation(summary = "회원가입", description = "유저 정보를 받아 회원가입을 처리합니다.")
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody UserDto user_dto) {

        //409 conflict 예외 처리 추가
        try{
            user_service.register(user_dto);
            return ResponseEntity.ok(Map.of("message","회원가입이 완료되었습니다."));
        }
        catch (DataIntegrityViolationException e){
            e.printStackTrace();  // 로그에 어떤 컬럼 충돌인지 출력됨 soo:250609
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "이미 존재하는 이메일입니다."));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "회원가입 중 서버 오류가 발생했습니다."));
        }
    }


    // 로그인 - 이메일, 비밀번호 받고 JWT 토큰 + userId 반환
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하고 JWT 토큰과 userId를 반환합니다.") //add250521
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String pw = loginRequest.get("pw");

        if (email == null || pw == null) {  // 간단한 입력 검증
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
    @Operation(summary = "로그아웃", description = "Authorization 헤더에서 JWT를 추출하고 로그아웃 처리") //edit250527
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("JWT 토큰이 없음");
        }

        String token = authHeader.substring(7); // "Bearer " 제거

        // 토큰 만료까지 남은 시간만큼 Redis에 저장
        long expiration = jwtUtil.getRemainingTime(token);
        redisTemplate.opsForValue().set(token, "logout", expiration, TimeUnit.MILLISECONDS);



        return ResponseEntity.ok("로그아웃 완료");

    }

    // 회원 정보 조회( pw 제외 )
    @Operation(summary = "회원 정보 조회", description = "userId로 회원 정보를 조회합니다. 비밀번호는 반환하지 않습니다.") //add250521
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserinfo(@PathVariable("userId") Integer user_id) {
        UserDto user_dto = user_service.getUserbyuserid(user_id);
        return ResponseEntity.ok(user_dto);

    }

    //마이페이지: 닉네임 업데이트
    @Operation(
            summary = "닉네임 수정",
            description = "사용자의 닉네임을 수정합니다.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PatchMapping("/{userId}/name")
    public ResponseEntity<String> updateUserName(
            @PathVariable int userId,
            @Valid @RequestBody UserNameUpdateDto dto) {

        user_service.updateUserName(userId, dto.getUserName());
        return ResponseEntity.ok("닉네임이 변경되었습니다.");
    }

    //마이페이지: 비밀번호 수정
    @Operation(summary = "비밀번호 변경", description = "현재 비밀번호 확인 후 새 비밀번호로 변경합니다.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PatchMapping("/{userId}/password")
    public ResponseEntity<String> updatePassword(
            @PathVariable int userId,
            @RequestBody PasswordUpdateDto dto) {

        user_service.updatePassword(userId, dto.getCurrentPw(), dto.getNewPw());
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }



}