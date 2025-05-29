package com.modam.backend.dto;
//메인 3. 완료된 내 모임 조회

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompletedBookClubDto {
    private Integer clubId;
    private String bookTitle;
    private String meetingDate;
    private String meetingSummary;
}
