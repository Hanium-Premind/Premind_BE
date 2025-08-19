package com.example.Premind_BE.domain.interview.dto.response;

import com.example.Premind_BE.domain.interview.dto.ai.response.PracticeTotalReportResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PracticeSubmitResDto {
    @Schema(description = "AI 세션 ID (추후 요청에 사용될 수 있으므로 값 저장해두기)")
    private String job_id;
    @Schema(description = "질문순서")
    private int sequence;
    @Schema(description = "전체 질문 수")
    private int total_question_num;
    @Schema(description = "질문")
    private String question;
    @Schema(description = "질문-답변에 대한 즉각 피드백")
    private String short_feedback;
    @Schema(description = "다음 질문")
    private String next_question;
    @Schema(description = "마지막 질문 여부 (마지막이면 true값)")
    private boolean finished;
    @Schema(description = "최종 분석 리포트 - 마지막 응답에서만 반환")
    private PracticeTotalReportResDto report;
}
