package com.example.Premind_BE.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "토큰 재발급 응답 DTO")
public class ReissueResDto {
    @Schema(description = "새로운 액세스 토큰")
    private String accessToken;

    @Schema(description = "새로운 리프레시 토큰 (옵션)")
    private String refreshToken;
}
