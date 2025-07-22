package com.example.Premind_BE.domain.resume.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "자기소개서 문항")
public class Section {

    @Schema(description = "자기소개서 질문 항목")
    private String question;

    @Schema(description = "사용자 답변")
    private String answer;
}
