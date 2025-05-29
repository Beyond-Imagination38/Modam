package com.modam.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    @Schema(description = "책 고유 ID", example = "1")
    private Integer bookId;

    @Schema(description = "책 제목", example = "1984")
    private String bookTitle;

    @Schema(description = "작가", example = "조지 오웰")
    private String author;

    @Schema(description = "장르", example = "디스토피아")
    private String genre;

    @Schema(description = "출판일", example = "1949-06-08")
    private LocalDate publishedDate;

    @Schema(description = "책 표지 이미지 URL", example = "/assets/images/1984.jpg")
    private String cover_image;
}
