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
public class ClubListDto {

    @Schema(description = "북클럽 고유 ID", example = "1")
    private Integer postId;

    @Schema(description = "주최자 유저 ID", example = "6")
    private Integer userId;

    @Schema(description = "도서 제목", example = "1984")
    private String title;

    @Schema(description = "모임 시작 시간", example = "2025-04-10 20:00")
    private String time;

    @Schema(description = "대표 이미지 URL", example = "/assets/images/1984.jpg")
    private String representativeImage;

    @Schema(description = "카테고리 (예: 진행 중, 완료, 좋아요)", example = "진행 중")
    private String category;

}
