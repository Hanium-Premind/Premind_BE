package com.example.Premind_BE.domain.portfolio.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "포트폴리오 문항")
public class PortfolioSectionReqDto {
    @Schema(description = "포트폴리오 질문 항목")
    private String question;

    @Schema(description = "사용자 답변")
    private String answer;
}
