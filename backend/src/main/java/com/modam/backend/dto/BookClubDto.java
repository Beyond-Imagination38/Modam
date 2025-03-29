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

    private int club_id;
    private String host_id;
    private String host_user_name;
    private String book_id;
    private LocalDateTime meeting_date;
    private String status;
    private int likes;
    private String summary;
    private String search_index;
    private LocalDateTime created_time;
    private LocalDateTime updated_time;
}
