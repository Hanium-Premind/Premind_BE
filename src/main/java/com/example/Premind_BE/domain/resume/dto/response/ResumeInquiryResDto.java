package com.example.Premind_BE.domain.resume.dto.response;

import com.example.Premind_BE.domain.job.domain.JobCategory;
import com.example.Premind_BE.domain.resume.domain.ResumeSection;
import com.example.Premind_BE.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeInquiryResDto {
    private Long id;
    private Long jobMajorId;
    private String jobMajorName;
    private Long jobMiddleId;
    private String jobMiddleName;
    private Long jobMinorId;
    private String jobMinorName;
    private String title;
    private String memo;
    private String company;
    private LocalDateTime createdDate;
    private List<ResumeSectionDto> sections = new ArrayList<>();

    public ResumeInquiryResDto(Long id,
                               Long jobMajorId, String jobMajorName,
                               Long jobMiddleId, String jobMiddleName,
                               Long jobMinorId, String jobMinorName,
                               String title, String memo, String company,
                               LocalDateTime createdDate) {
        this.id = id;
        this.jobMajorId = jobMajorId;
        this.jobMajorName = jobMajorName;
        this.jobMiddleId = jobMiddleId;
        this.jobMiddleName = jobMiddleName;
        this.jobMinorId = jobMinorId;
        this.jobMinorName = jobMinorName;
        this.title = title;
        this.memo = memo;
        this.company = company;
        this.createdDate = createdDate;
    }

}
