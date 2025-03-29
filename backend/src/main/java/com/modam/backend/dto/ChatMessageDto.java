package com.modam.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {

    private int club_id;
    private String user_id;
    private String user_name;
    private String content;
    private LocalDateTime created_time;
}
