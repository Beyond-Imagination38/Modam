package com.modam.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
    private int userId;

    @Schema(description = "유저 이름", example = "사용자01")
    @NotBlank(message = "닉네임은 필수입니다.")
    private String userName;

    @Schema(description = "유저 이메일", example = "user01@example.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "유효한 이메일 형식이어야 합니다.")
    private String email;

    @Schema(description = "비밀번호", example = "pw01")
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String pw;

    @Schema(description = "프로필 이미지 URL (없을 경우 NULL)", example = "NULL")
    private String profileImage;

}
