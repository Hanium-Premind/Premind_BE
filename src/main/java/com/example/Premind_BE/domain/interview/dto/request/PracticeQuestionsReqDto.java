package com.example.Premind_BE.domain.interview.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "면접 옵션을 기반으로 면접 질문을 생성하는 요청 DTO")
public class PracticeQuestionsReqDto {
    @Schema(description = "선택한 자소서 id")
    private Long resumeId;

    @Schema(description = "선택한 포트폴리오 id")
    private Long portfolioId;

    @Schema(description = "면접관 스타일 (반드시, STRICT, KIND, JOB_SPECIFIC 값들로 요청 보내기)")
    private String interviewerStyle;

    @Schema(description = "질문 갯수")
    private int numQuestions;

    @Schema(description = "면접 유형")
    private List<String> interviewTypes = new ArrayList<>();

    // 직무 면접 선택시 직무 id도 추가 예정

}
