package com.example.Premind_BE.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "리프레시 토큰 요청 관련 DTO")
public class ReissueReqDto {
    @Schema(description = "리프레시 토큰")
    private String refreshToken;
}
