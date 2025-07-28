package com.example.Premind_BE.domain.portfolio.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "포트폴리오 상세조회시 포트폴리오 질문-답변 항목 응답 DTO")
public class PortfolioSectionResDto {
    @Schema(description = "포트폴리오 항목 ID 값")
    private Long id;
    @Schema(description = "포트폴리오 질문 순서")
    private int sequence;
    @Schema(description = "포트폴리오 질문 항목")
    private String question;
    @Schema(description = "포트폴리오 답변 항목")
    private String answer;
}
