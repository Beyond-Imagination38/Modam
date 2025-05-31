package com.modam.backend.controller;


import com.modam.backend.dto.ReadingNoteDto;
import com.modam.backend.service.ReadingNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reading-notes")
@RequiredArgsConstructor
public class ReadingNoteController {

    private final ReadingNoteService readingNoteService;

    @Operation(
            summary = "독후감 저장 (처음 작성 또는 덮어쓰기)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(value = """
                            {
                              "userId": 3,
                              "clubId": 1,
                              "content": "이 책은 전체주의의 무서움을 강하게 느끼게 했습니다."
                            }
                            """)
                    )
            )
    )
    @PostMapping
    public ResponseEntity<String> saveReadingNote(@RequestBody ReadingNoteDto dto) {
        readingNoteService.saveNote(dto);
        return ResponseEntity.ok("독후감이 저장되었습니다.");
    }

    @Operation(
            summary = "독후감 조회",
            description = "사용자 ID와 클럽 ID를 기준으로 작성한 독후감을 불러옵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "404", description = "독후감 없음")
            }
    )
    @GetMapping
    public ResponseEntity<ReadingNoteDto> getReadingNote(
            @RequestParam int clubId,
            @RequestParam int userId
    ) {
        ReadingNoteDto dto = readingNoteService.getNote(clubId, userId);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "독후감 수정",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(value = """
                            {
                              "userId": 3,
                              "clubId": 1,
                              "content": "처음보다 더 정제된 감상을 추가했습니다."
                            }
                            """)
                    )
            )
    )
    @PutMapping
    public ResponseEntity<String> updateReadingNote(@RequestBody ReadingNoteDto dto) {
        readingNoteService.updateNote(dto);
        return ResponseEntity.ok("독후감이 수정되었습니다.");
    }
}
