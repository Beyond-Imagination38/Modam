package com.modam.backend.controller;

import com.modam.backend.dto.BookClubCreateDto;
import com.modam.backend.dto.ClubListDto;
import com.modam.backend.model.BookClub;
import com.modam.backend.service.BookClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookclubs")
@RequiredArgsConstructor
public class BookClubController {

    private final BookClubService bookClubService;

    @GetMapping
    public ResponseEntity<List<ClubListDto>> getAllClubs() {
        return ResponseEntity.ok(bookClubService.getAllClubSummaries());
    }

    @GetMapping("/{clubId}")
    public ResponseEntity<ClubListDto> getBookClubById(@PathVariable int clubId){
        return ResponseEntity.ok(bookClubService.getClubSummaryById(clubId));
    }

    @PostMapping
    public ResponseEntity<BookClub> createBookClub(@RequestBody BookClubCreateDto dto) {
        BookClub createdClub = bookClubService.createBookClub(dto);
        return ResponseEntity.ok(createdClub);
    }
}