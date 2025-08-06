package com.example.Premind_BE.domain.interview.dto.response;

import com.example.Premind_BE.domain.portfolio.domain.Portfolio;
import com.example.Premind_BE.domain.resume.domain.Resume;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioResDto {
    private Long id;
    private String title;

    public static PortfolioResDto from(Portfolio portfolio) {
        PortfolioResDto dto = new PortfolioResDto();
        dto.id = portfolio.getId();
        dto.title = portfolio.getTitle();
        return dto;
    }
}
