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
    private Long memoId;
    private int clubId;
    private String userId;
    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
