package com.example.Premind_BE.domain.interview.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "연습모드 첫 번째 질문 생성 응답")
public class PracticeQuestionResDto {
    @Schema(description = "면접 기록 id (추후 요청에 사용될 수 있으므로 값 저장해두기)")
    private Long interview_record_id;
    @Schema(description = "질문에 대한 AI 세션 ID (추후 요청에 사용될 수 있으므로 값 저장해두기)")
    private String job_id;
    @Schema(description = "연습모드 첫 번째 질문")
    private String question;
    @Schema(description = "질문순서")
    private int sequence;
    @Schema(description = "전체 질문 수")
    private int total_question_num;
}
