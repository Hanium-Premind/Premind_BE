package com.example.Premind_BE.domain.resume.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeUploadReqDto {
    private Long jobMajorId;
    private Long jobMiddleId;
    private Long jobMinorId;
    private String title;
    private String memo;
    private String company;
    private List<Section> qaList;
}
