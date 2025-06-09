package com.modam.backend.dto;
// 요약 저장
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryCreateDto {

    @Schema(description = "요약 대상 주제 번호", example = "1")
    private Integer topicNumber;  // 1, 2, 3 ...

    @Schema(description = "요약 주제 질문", example = "전체주의 사회의 특징은 무엇인가?")
    private String topic;         // 주제명

    @Schema(description = "요약 내용", example = "참가자들은 감시 체제와 언어 통제를 주요 특징으로 언급했습니다.")
    private String content;       // 요약 내용
}
