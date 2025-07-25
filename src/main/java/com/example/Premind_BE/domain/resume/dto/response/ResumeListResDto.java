package com.example.Premind_BE.domain.resume.dto.response;

import com.example.Premind_BE.domain.resume.domain.Resume;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "자기소개서 리스트 반환을 위한 응답 DTO")
public class ResumeListResDto {
    @Schema(description = "자기소개서 id")
    private Long id;
    @Schema(description = "자기소개서 제목")
    private String title;
    @Schema(description = "자기소개서 메모 내용")
    private String memo;
    @Schema(description = "자기소개서 지원 직무")
    private String jobName;
    @Schema(description = "자기소개서 지원 기업")
    private String company;
    @Schema(description = "등록일자")
    private LocalDateTime createdDate;

    public static ResumeListResDto from(Resume resume) {
        ResumeListResDto dto = new ResumeListResDto();
        dto.id = resume.getId();
        dto.title = resume.getTitle();
        dto.memo = resume.getMemo();
        dto.jobName = resume.getJobMinor().getName();
        dto.company = resume.getCompany();
        dto.createdDate = resume.getCreatedDate();
        return dto;
    }
}
