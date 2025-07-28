package com.example.Premind_BE.domain.portfolio.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "포트폴리오 상세조회 응답 DTO")
public class PortfolioInquiryResDto {
    @Schema(description = "포트폴리오 ID값")
    private Long id;
    @Schema(description = "포트폴리오 직무 대분류 ID")
    private Long jobMajorId;
    @Schema(description = "포트폴리오 직무 대분류 이름")
    private String jobMajorName;
    @Schema(description = "포트폴리오 직무 중분류 ID")
    private Long jobMiddleId;
    @Schema(description = "포트폴리오 직무 중분류 이름")
    private String jobMiddleName;
    @Schema(description = "포트폴리오 직무 소분류 ID")
    private Long jobMinorId;
    @Schema(description = "포트폴리오 직무 소분류 이름")
    private String jobMinorName;
    @Schema(description = "포트폴리오 제목")
    private String title;
    @Schema(description = "포트폴리오 지원 기업")
    private String company;
    @Schema(description = "포트폴리오 pdf 파일 경로")
    private String filePath;
    @Schema(description = "포트폴리오 생성 일자")
    private LocalDateTime createdDate;
    @Schema(description = "포트폴리오 질문-답변 항목 리스트")
    private List<PortfolioSectionResDto> sections = new ArrayList<>();

    public PortfolioInquiryResDto(Long id,
                                  Long jobMajorId, String jobMajorName,
                                  Long jobMiddleId, String jobMiddleName,
                                  Long jobMinorId, String jobMinorName,
                                  String title, String company, String filePath,
                                  LocalDateTime createdDate) {
        this.id = id;
        this.jobMajorId = jobMajorId;
        this.jobMajorName = jobMajorName;
        this.jobMiddleId = jobMiddleId;
        this.jobMiddleName = jobMiddleName;
        this.jobMinorId = jobMinorId;
        this.jobMinorName = jobMinorName;
        this.title = title;
        this.company = company;
        this.filePath = filePath;
        this.createdDate = createdDate;
    }
}
