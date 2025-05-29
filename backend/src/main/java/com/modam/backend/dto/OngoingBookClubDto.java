package com.modam.backend.dto;
//메인 2. 진행 중인 내 모임 조회

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OngoingBookClubDto {
    private Integer clubId;
    private String bookTitle;
    private String meetingDate;
    private String description;
}