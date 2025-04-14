package com.modam.backend.controller;

import com.modam.backend.dto.MemoDto;
import com.modam.backend.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memo")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/{clubId}/{userId}")
    public ResponseEntity<MemoDto> getMemo(@PathVariable Integer clubId, @PathVariable Integer userId) {
        MemoDto memoDto = memoService.getMemo(clubId, userId);
        return ResponseEntity.ok(memoDto);
    }

    @PostMapping("/{clubId}/{userId}")
    public ResponseEntity<MemoDto> saveMemo(@PathVariable Integer clubId,
                                            @PathVariable Integer userId,
                                            @RequestBody MemoDto memoDto) {
        MemoDto saved = memoService.saveOrUpdateMemo(clubId, userId, memoDto.getContent());
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/{clubId}/{userId}/finalize")
    public ResponseEntity<String> finalizeMemo(@PathVariable Integer clubId,
                                               @PathVariable Integer userId) {
        memoService.finalizeMemo(clubId, userId);
        return ResponseEntity.ok("메모가 확정되었습니다.");
    }
}