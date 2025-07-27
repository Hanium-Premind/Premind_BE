package com.example.Premind_BE.domain.portfolio.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Presigned URL 응답 DTO")
public class PresignedUrlResDto {
    @Schema(description = "업로드용 presigned URL")
    private String presignedUrl;

    @Schema(description = "S3 내 파일 경로 (fileKey)")
    private String fileKey;
}

