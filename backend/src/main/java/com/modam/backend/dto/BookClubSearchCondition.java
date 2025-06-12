package com.modam.backend.dto;
//메인 1. 전체 독서모임 조회 + 검색, 정렬, 필터

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "독서모임 전체조회: 검색 조건")
public class BookClubSearchCondition {

    @Schema(description = "검색 키워드 (책 제목 또는 설명 포함)", example = "1984")
    private String keyword;

    @Schema(description = "모임 상태 필터: PENDING, ONGOING, COMPLETED", example = "ONGOING")
    private String status;

    @Schema(description = "정렬 기준: latest, meetingDate", example = "latest")
    private String sortBy;

}
