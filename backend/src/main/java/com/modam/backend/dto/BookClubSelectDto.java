package com.modam.backend.dto;
//메인 1. 전체 독서모임 조회 + 검색, 정렬, 필터

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "독서모임 전체조회: 응답 DTO")
public class BookClubSelectDto {

    @Schema(description = "모임 ID", example = "10")
    private Integer clubId;

    @Schema(description = "책 제목", example = "1984")
    private String bookTitle;

    @Schema(description = "모임 일시", example = "2025-06-01 20:00")
    private String meetingDate;

    @Schema(description = "책 커버 이미지 URL", example = "https://image.url")
    private String coverImage;

    @Schema(description = "모임 상태 (PENDING / ONGOING / COMPLETED)", example = "PENDING")
    private String status;


}
