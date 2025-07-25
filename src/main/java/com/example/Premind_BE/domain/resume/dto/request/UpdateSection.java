package com.example.Premind_BE.domain.resume.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSection {
    @Schema(description = "자기소개서 질문 항목")
    private String question;

    @Schema(description = "사용자 답변")
    private String answer;

    @Schema(description = "글자수")
    private Integer characterCount;
}
