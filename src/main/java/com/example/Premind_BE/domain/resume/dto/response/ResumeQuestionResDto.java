package com.example.Premind_BE.domain.resume.dto.response;

import com.example.Premind_BE.domain.resume.domain.ResumeQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "자소서 기본 질문 항목 리스트 응답 DTO")
public class ResumeQuestionResDto {
    private Long id;
    private String question;

    public static ResumeQuestionResDto from(ResumeQuestion resumeQuestion) {
        ResumeQuestionResDto dto = new ResumeQuestionResDto();
        dto.id = resumeQuestion.getId();
        dto.question = resumeQuestion.getQuestion();
        return dto;
    }
}
