package com.modam.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookClubDto {
    private int clubId;
    private String hostId;
    private String hostUserName;
    private String bookId;
    private LocalDateTime meetingDate;
    private String status;
    private int likes;
    private String summary;
    private String searchIndex;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
