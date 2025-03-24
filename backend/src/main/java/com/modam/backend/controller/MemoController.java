
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

    private final MemoService memoService;

    @PostMapping("/{roomId}/memo")
    public MemoDto saveMemo(@PathVariable("roomId") int roomId, @RequestBody MemoDto dto) {
        dto.setClubId(roomId); // URL에서 받은 roomId를 DTO에 설정
        return memoService.saveMemo(dto);
    }

    @PostMapping("/{roomId}/memos")
    public List<MemoDto> getMemos(@PathVariable("roomId") int roomId) {
        return memoService.getMemosByClubId(roomId);
    }
}
*/
