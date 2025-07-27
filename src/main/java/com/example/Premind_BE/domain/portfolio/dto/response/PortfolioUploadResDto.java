package com.example.Premind_BE.domain.portfolio.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "포트폴리오 업로드 응답 DTO")
public class PortfolioUploadResDto {
    @Schema(description = "생성된 포트폴리오 id값")
    private Long portfolioId;
}
