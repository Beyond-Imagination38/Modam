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

    // 메인 3. 완료된 내 독서모임 조회 기능
    @GetMapping("/my/completed")
    @Operation(
            summary = "완료된 내 독서모임 조회",
            description = "userId가 참여한 독서모임 중 완료(COMPLETED)된 모임 리스트를 반환합니다."
    )
    public ResponseEntity<List<BookClubCommonDto>> getMyCompletedClubs(@RequestParam int userId) {
        return ResponseEntity.ok(bookClubService.getCompletedClubsByUserId(userId));
    }

    // 상세 1. 상태 판단 로직
    @GetMapping("/{clubId}/status")
    @Operation(summary = "모임 상세 상태 판단", description = "유저 기준으로 모임 상태(OPEN/CLOSED/ONGOING/COMPLETED)를 반환합니다.")
    public ResponseEntity<BookClubStatusDto> getClubStatus(
            @PathVariable int clubId,
            @RequestParam int userId
    ) {
        return ResponseEntity.ok(bookClubService.getBookClubStatus(clubId, userId));
    }


    //상세 1. 모임 내용 반환
    @Operation(summary = "모임 상세 정보 반환", description = "북클럽의 상세 정보 + 현재 유저의 참여 여부 + 상태 반환")
    @GetMapping("/{clubId}/detail")
    public ResponseEntity<BookClubDetailDto> getBookClubDetail(
            @PathVariable int clubId,
            @RequestParam int userId
    ) {
        return ResponseEntity.ok(bookClubService.getBookClubDetail(clubId, userId));
    }



    //상세 2. 모임 신청 API (/join)
    @PostMapping("/{clubId}/join")
    @Operation(summary = "모임 신청", description = """
        주어진 clubId와 userId로 독서 모임 신청을 시도합니다.
        
        - 신청 성공 조건: 상태가 OPEN이고, 해당 사용자가 아직 신청하지 않은 경우
        - 실패 예외:
          - 모집 마감 (CLOSED)
          - 이미 진행 중인 모임 (ONGOING)
          - 이미 완료된 모임 (COMPLETED)
          - 이미 신청된 사용자
        """)
    public ResponseEntity<?> joinBookClub(@PathVariable int clubId, @RequestParam int userId) {
        BookClubStatusDto statusDto = bookClubService.getBookClubStatus(clubId, userId);

        switch (statusDto.getStatus()) {
            case "OPEN":
                try {
                    bookClubService.joinBookClub(clubId, userId);
                    return ResponseEntity.ok("참여 신청이 완료되었습니다.");
                } catch (RuntimeException e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }

            case "CLOSED":
                return ResponseEntity.badRequest().body("모집이 마감된 모임입니다.");

            case "ONGOING":
                return ResponseEntity.badRequest().body("이미 진행 중인 모임입니다.");

            case "COMPLETED":
                return ResponseEntity.badRequest().body("이미 종료된 모임입니다.");

            default:
                return ResponseEntity.badRequest().body("참여할 수 없는 상태입니다.");
        }
    }

/*    @PostMapping("/{clubId}/join")
    @Operation(
            summary = "모임 신청",
            description = "해당 유저가 클럽에 신청합니다. (status가 OPEN일 때만 신청 가능)"
    )
    public ResponseEntity<String> joinBookClub(
            @PathVariable int clubId,
            @RequestParam int userId
    ) {
        bookClubService.joinBookClub(clubId, userId);
        return ResponseEntity.ok("모임 신청이 완료되었습니다.");
    }*/






        //단건 조회용으로 추가?
    /*    @GetMapping("/{clubId}")
    public ResponseEntity<ClubListDto> getBookClubById(@PathVariable int clubId){
        return ResponseEntity.ok(bookClubService.getClubSummaryById(clubId));
    }*/



}