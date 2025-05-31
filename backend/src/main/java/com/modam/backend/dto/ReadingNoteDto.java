package com.modam.backend.dto;
//독후감 1. 저장

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadingNoteDto {
    @Schema(description = "독후감을 작성한 사용자 ID", example = "3")
    private int userId;

    @Schema(description = "독후감을 작성한 클럽(모임) ID", example = "1")
    private int clubId;

    @Schema(description = "사용자가 작성한 독후감 본문", example = "이 책은 전체주의의 무서움을 강하게 느끼게 했습니다. 현실과도 닮아 있어 인상 깊었습니다.")
    private String content;
}
