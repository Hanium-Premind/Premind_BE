package com.example.Premind_BE.domain.portfolio.dto.response;

import com.example.Premind_BE.domain.portfolio.domain.Portfolio;
import com.example.Premind_BE.domain.resume.domain.Resume;
import com.example.Premind_BE.domain.resume.dto.response.ResumeListResDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioListResDto {
    @Schema(description = "포트폴리오 id")
    private Long id;
    @Schema(description = "포트폴리오 제목")
    private String title;
    @Schema(description = "포트폴리오 지원 직무")
    private String jobName;
    @Schema(description = "포트폴리오 지원 기업")
    private String company;
    @Schema(description = "등록일자")
    private LocalDateTime createdDate;

    public static PortfolioListResDto from(Portfolio portfolio) {
        PortfolioListResDto dto = new PortfolioListResDto();
        dto.id = portfolio.getId();
        dto.title = portfolio.getTitle();
        dto.jobName = portfolio.getJobMinor().getName();
        dto.company = portfolio.getCompany();
        dto.createdDate = portfolio.getCreatedDate();
        return dto;
    }
}
