package com.example.Premind_BE.domain.interview.dto.response;

import com.example.Premind_BE.domain.portfolio.domain.PortfolioQuestion;
import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioQuestionResDto;
import com.example.Premind_BE.domain.resume.domain.Resume;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeResDto {
    private Long id;
    private String title;

    public static ResumeResDto from(Resume resume) {
        ResumeResDto dto = new ResumeResDto();
        dto.id = resume.getId();
        dto.title = resume.getTitle();
        return dto;
    }
}
