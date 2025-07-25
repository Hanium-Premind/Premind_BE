package com.example.Premind_BE.domain.resume.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeSectionDto {
    private Long id;
    private int sequence;
    private String question;
    private String answer;
    private Integer characterCount;
}
