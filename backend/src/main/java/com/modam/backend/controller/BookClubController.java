package com.modam.backend.controller;

import com.modam.backend.dto.ClubListDto;
import com.modam.backend.service.BookClubService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}