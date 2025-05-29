package com.modam.backend.controller;

import com.modam.backend.dto.*;
import com.modam.backend.model.BookClub;
import com.modam.backend.service.BookClubService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookclubs")
@RequiredArgsConstructor
public class BookClubController {

    private final BookClubService bookClubService;

    @PostMapping
    public ResponseEntity<BookClub> createBookClub(@RequestBody BookClubCreateDto dto) {
        return ResponseEntity.ok(bookClubService.createBookClub(dto));
    }


    //메인 1. 전체 독서모임 검색/정렬/필터
    @GetMapping("/search")
    @Operation(
            summary = "전체 독서모임 검색/정렬/필터",
            description = "keyword(책 제목/설명), status(PENDING/ONGOING/COMPLETED), sortBy(latest/likes/meetingDate) 파라미터로 필터링된 독서모임 리스트 반환"
    )
    public ResponseEntity<List<BookClubCommonDto>> searchBookClubs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String sortBy
    ) {
        BookClubSearchCondition condition = new BookClubSearchCondition();
        condition.setKeyword(keyword);
        condition.setStatus(status);
        condition.setSortBy(sortBy);
        return ResponseEntity.ok(bookClubService.searchBookClubs(condition));
    }


    //메인 2. 진행 중인 내 모임 조회 기능
    @GetMapping("/my/ongoing")
    @Operation(
            summary = "진행 중인 내 독서모임 조회",
            description = "userId가 참여한 독서모임 중 진행 중(ONGOING)인 모임 리스트 반환"
    )
    public ResponseEntity<List<BookClubCommonDto>> getMyOngoingClubs(@RequestParam int userId) {
        return ResponseEntity.ok(bookClubService.getOngoingClubsByUserId(userId));
    }






    //단건 조회용으로 추가?
    /*    @GetMapping("/{clubId}")
    public ResponseEntity<ClubListDto> getBookClubById(@PathVariable int clubId){
        return ResponseEntity.ok(bookClubService.getClubSummaryById(clubId));
    }*/



}