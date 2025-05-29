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
        BookClub createdClub = bookClubService.createBookClub(dto);
        return ResponseEntity.ok(createdClub);
    }

    //전체 독서모임 검색/정렬/필터
    @GetMapping("/search")
    @Operation(
            summary = "전체 독서모임 검색/정렬/필터",
            description = "keyword(책 제목/설명), status(PENDING/ONGOING/COMPLETED), sortBy(latest/likes/meetingDate) 파라미터로 필터링된 독서모임 리스트를 반환합니다."
    )
    public ResponseEntity<List<BookClubSelectDto>> searchBookClubs(
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

    //단건 조회용으로 추가?
/*    @GetMapping("/{clubId}")
    public ResponseEntity<ClubListDto> getBookClubById(@PathVariable int clubId){
        return ResponseEntity.ok(bookClubService.getClubSummaryById(clubId));
    }*/



}