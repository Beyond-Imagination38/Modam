package com.modam.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
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
    private LocalDateTime createdTime;

    @Schema(description = "메모 수정 시간", example = "2025-04-10T16:25:00")
    private LocalDateTime updatedTime;

    private Boolean isFinalized = false;

    // 조회용 생성자 (전체 필드용)
    public MemoDto(Integer memoId, Integer clubId, Integer userId, String content,
                   LocalDateTime createdTime, LocalDateTime updatedTime, Boolean isFinalized) {
        this.memoId = memoId;
        this.clubId = clubId;
        this.userId = userId;
        this.content = content;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.isFinalized = isFinalized;
    }

    // 요약용 (필요 최소 필드만)
    public MemoDto(Integer userId, Integer clubId, String content, Boolean isFinalized) {
        this.userId = userId;
        this.clubId = clubId;
        this.content = content;
        this.isFinalized = isFinalized;
    }

}