package com.modam.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(description = "유저 고유 ID", example = "1")
    private int user_id;

    @Schema(description = "유저 이름", example = "사용자01")
    private String user_name;

    @Schema(description = "유저 이메일", example = "user01@example.com")
    private String email;

    @Schema(description = "비밀번호", example = "pw01")
    private String pw;

    @Schema(description = "프로필 이미지 URL (없을 경우 NULL)", example = "NULL")
    private String profile_image;

    @Schema(description = "보유 코인", example = "0")
    private int coin;
}
