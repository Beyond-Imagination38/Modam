package com.modam.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "독서 모임 개설 요청 DTO")
public class BookClubCreateDto {

    @Schema(description = "호스트 사용자 ID", example = "1", required = true)
    private Integer hostId;

    //@Schema(description = "책 ID", example = "10", required = true)
    //private Integer bookId;

    @Schema(description = "책 제목", example = "1984", required = true)
    private String bookTitle;

    @Schema(description = "모임 날짜", example = "2025-04-10", required = true)
    private LocalDate date;

    @Schema(description = "모임 시간", example = "20:00", required = true)
    private LocalTime time;

    @Schema(description = "모임 설명", example = "1984를 읽고 나누는 첫 번째 모임")
    private String clubDescription;
}
