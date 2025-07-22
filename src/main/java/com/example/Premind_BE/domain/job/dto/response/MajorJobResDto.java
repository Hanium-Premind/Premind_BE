package com.example.Premind_BE.domain.job.dto.response;

import com.example.Premind_BE.domain.job.domain.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지원 직무 대분류 리스트 조회 응답 DTO")
public class MajorJobResDto {
    @Schema(description = "대분류 ID")
    private Long id;
    @Schema(description = "대분류 직무명")
    private String name;
    @Schema(description = "대분류")
    private Level level;
}
