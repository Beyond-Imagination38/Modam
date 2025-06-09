package com.modam.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateDto {
    @Schema(description = "현재 비밀번호", example = "pw11")
    private String currentPw;

    @Schema(description = "새 비밀번호", example = "newPw11!")
    private String newPw;

}
