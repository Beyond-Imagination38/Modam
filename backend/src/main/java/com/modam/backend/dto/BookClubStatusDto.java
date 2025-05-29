package com.modam.backend.dto;

//상세 1. 상세페이지 상태 판단 로직
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookClubStatusDto {

    private Integer clubId;
    private Integer userId;
    private String status; // OPEN / CLOSED / ONGOING / COMPLETED


}