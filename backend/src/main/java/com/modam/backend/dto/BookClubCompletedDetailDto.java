package com.modam.backend.dto;
// 6. 완료모임 상세페이지
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookClubCompletedDetailDto {
    @Schema(description = "북클럽 ID", example = "2")
    private int clubId;

    @Schema(description = "책 제목", example = "군주론")
    private String bookTitle;

    @Schema(description = "책 저자", example = "마키아벨리")
    private String bookAuthor;

    @Schema(description = "표지 이미지 URL")
    private String coverImage;

    @Schema(description = "모임 일시", example = "2025-02-13T19:30:00")
    private LocalDateTime meetingDate;

    @Schema(description = "요약 목록")
    private List<SummaryCreateDto> summaries;

    @Schema(description = "독후감 목록")
    private List<ReadingNoteDto> readingNotes;

    @Schema(description = "메모 목록")
    private List<MemoDto> memos;
}
