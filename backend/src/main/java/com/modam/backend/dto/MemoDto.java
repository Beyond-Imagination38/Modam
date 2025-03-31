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
public class MemoDto {

    private Long memo_id;
    private int club_id;
    private String user_id;
    private String content;
    private LocalDateTime created_time;
    private LocalDateTime updated_time;
}
