package com.modam.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AITopicRequestDto {
    private int book_id;
    private List<String> user_responses;
}
