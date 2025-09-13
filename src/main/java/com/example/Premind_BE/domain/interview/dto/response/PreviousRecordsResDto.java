package com.example.Premind_BE.domain.interview.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "이전면접 기록 조회 응답 dto")
public class PreviousRecordsResDto {
    private Long id;
    @Schema(description = "면접 생성일자")
    private LocalDateTime createdDate;
    @Schema(description = "직무명")
    private String jobName;
    @Schema(description = "지원 회사")
    private String company;
    @Schema(description = "자료")
    private String data;
    @Schema(description = "면접 점수")
    private int score;
    @Schema(description = "면접 모드")
    private String interviewMode;

}
