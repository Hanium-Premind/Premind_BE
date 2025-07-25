package com.example.Premind_BE.domain.resume.dto;

import com.example.Premind_BE.domain.resume.dto.request.Section;
import com.example.Premind_BE.domain.resume.dto.request.UpdateSection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "자소서 수정을 위한 요청 DTO")
public class ResumeUpdateDto {
    @Schema(description = "선택한 직무의 대분류 ID값")
    private Long jobMajorId;
    @Schema(description = "선택한 직무의 중분류 ID값")
    private Long jobMiddleId;
    @Schema(description = "선택한 직무의 소분류 ID값")
    private Long jobMinorId;
    @Schema(description = "자소서 제목")
    private String title;
    @Schema(description = "자소서 메모 내용")
    private String memo;
    @Schema(description = "자소서 지원 기업명")
    private String company;
    @Schema(description = "자소서 질문-답변 항목 리스트")
    private List<UpdateSection> qaList;
}
