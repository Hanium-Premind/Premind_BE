package com.example.Premind_BE.domain.portfolio.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "포트폴리오 수정을 위한 요청 DTO")
public class PortfolioUpdateReqDto {
    @Schema(description = "선택한 직무의 대분류 ID값")
    private Long jobMajorId;
    @Schema(description = "선택한 직무의 중분류 ID값")
    private Long jobMiddleId;
    @Schema(description = "선택한 직무의 소분류 ID값")
    private Long jobMinorId;
    @Schema(description = "포트폴리오 제목")
    private String title;
    @Schema(description = "포트폴리오 지원 기업명")
    private String company;
    @Schema(description = "포트폴리오 질문-답변 항목 리스트")
    private List<PortfolioSectionReqDto> qaList;
    @Schema(description = "업로드된 S3 파일 경로, presignedUrl.split('?')[0] 값이다.")
    private String fileUrl;
}
