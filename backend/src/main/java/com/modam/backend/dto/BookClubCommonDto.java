package com.modam.backend.dto;
/**
 * 공통 독서모임 정보 DTO
 * 전체 조회, 내 모임 조회, 상세 요약 등에서 공통적으로 사용하는 응답 포맷
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "공통 독서모임 정보 DTO")
public class BookClubCommonDto {

    @Schema(description = "책 커버 이미지 URL", example = "/assets/images/1984.jpg")
    private String coverImage;

    @Schema(description = "책 제목", example = "1984")
    private String bookTitle;

    @Schema(description = "모임 일시", example = "2025-04-11 20:00")
    private String meetingDateTime;

    @Schema(description = "모임 설명", example = "이 책을 읽고 생각을 나누는 시간입니다.")
    private String clubDescription;

    @Schema(description = "참여 인원 현황 (예: 3/4)")
    private String participants;
}
