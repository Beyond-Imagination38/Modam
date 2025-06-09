package com.modam.backend.dto;
// 마이페이지 - 닉네임 수정

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNameUpdateDto {

    @Schema(description = "새 닉네임", example = "새로운닉네임")
    @NotBlank(message = "닉네임은 비어 있을 수 없습니다.")
    private String userName;
}
