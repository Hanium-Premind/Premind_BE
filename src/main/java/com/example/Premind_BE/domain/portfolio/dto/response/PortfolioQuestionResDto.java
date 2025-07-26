package com.example.Premind_BE.domain.portfolio.dto.response;

import com.example.Premind_BE.domain.portfolio.domain.PortfolioQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioQuestionResDto {
    private Long id;
    private String question;

    public static PortfolioQuestionResDto from(PortfolioQuestion portfolioQuestion) {
        PortfolioQuestionResDto dto = new PortfolioQuestionResDto();
        dto.id = portfolioQuestion.getId();
        dto.question = portfolioQuestion.getQuestion();
        return dto;
    }
}
