package com.modam.backend.dto;
// 상세 1. 모임 반환
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
public class BookClubDetailDto {

    @Schema(description = "사용자 ID", example = "1")
    private Integer userId;

    @Schema(description = "북클럽 고유 ID", example = "2")
    private Integer clubId;

    @Schema(description = "모임 제목 (책 제목)", example = "군주론")
    private String title;

    @Schema(description = "책 제목", example = "군주론")
    private String bookTitle;

    @Schema(description = "모임 설명", example = "군주론을 읽고 토론하는 모임입니다.")
    private String description;

    @Schema(description = "책 표지 이미지 URL", example = "https://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "모임 날짜 및 시간", example = "2025-02-13T19:30:00")
    private LocalDateTime meetingDate;

    @Schema(description = "현재 확정된 참여자 수", example = "2")
    private int currentParticipants;

    @Schema(description = "최대 참여자 수", example = "4")
    private int maxParticipants;

    @Schema(description = "현재 유저의 참여 여부", example = "true")
    private boolean isParticipating;

    @Schema(description = "모임 상태", example = "OPEN", allowableValues = {"OPEN", "CLOSED", "ONGOING", "COMPLETED"})
    private String status;
}
