
//추후에 MEMO 레포와 함께 추가

/*
package com.modam.backend.controller;

import com.modam.backend.dto.MemoDto;
import com.modam.backend.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memo_service;

    @PostMapping("/{room_id}/memo")
    public MemoDto saveMemo(@PathVariable("room_id") int room_id, @RequestBody MemoDto dto) {
        dto.setClubId(room_id); // URL에서 받은 room_id를 DTO에 설정
        return memo_service.saveMemo(dto);
    }

    @PostMapping("/{room_id}/memos")
    public List<MemoDto> getMemos(@PathVariable("room_id") int room_id) {
        return memo_service.getMemosbyclubid(room_id);
    }
}
*/

