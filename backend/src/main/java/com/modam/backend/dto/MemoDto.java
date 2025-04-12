package com.modam.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemoDto {

    @Schema(description = "메모 고유 ID", example = "901")
    private Long memo_id;

    @Schema(description = "북클럽 ID", example = "9")
    private int club_id;

    @Schema(description = "작성자 유저 ID", example = "user1")
    private String user_id;

    @Schema(description = "메모 내용", example = "모임 중 핵심 토픽 정리 메모입니다.")
    private String content;

    //추후 수정
    @Schema(description = "메모 작성 시간", example = "2025-04-10T16:20:00")
    private LocalDateTime created_time;

    //추후 수정
    @Schema(description = "메모 수정 시간", example = "2025-04-10T16:25:00")
    private LocalDateTime updated_time;
}
