package com.example.Premind_BE.domain.interview.dto.ai.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ai측에 질문에 대한 답변 제출시 반환받는 응답
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PracticeQAFeedbackResDto {

    private String job_id;
    private int turn;
    private int total_turns;
    private String question;
    private String short_feedback;
    private boolean finished;
    private String next_question;
    private String finished_at;
    private PracticeTotalReportResDto report;
    private String answer_text;
}
