package com.modam.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemoDto {

    @Schema(description = "메모 고유 ID", example = "901")
    private Integer memoId;

    @Schema(description = "북클럽 ID", example = "9")
    private Integer clubId;

    @Schema(description = "작성자 유저 ID", example = "3")
    private Integer userId;

    @Schema(description = "메모 내용", example = "모임 중 핵심 토픽 정리 메모입니다.")
    private String content;

    @Schema(description = "메모 작성 시간", example = "2025-04-10T16:20:00")
    private LocalDateTime created_time;

    @Schema(description = "메모 수정 시간", example = "2025-04-10T16:25:00")
    private LocalDateTime updated_time;

    private Boolean isFinalized = false;
}