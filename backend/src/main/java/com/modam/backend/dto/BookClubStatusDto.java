package com.modam.backend.dto;

//상세 1. 상세페이지 상태 조회 (신청 가능 여부, 진행 여부 등)
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookClubStatusDto {
    private Integer clubId;
    private String status; // 신청전 / 모집마감 / 진행중 / 완료
    private boolean isJoined;
    private boolean isFull;
}