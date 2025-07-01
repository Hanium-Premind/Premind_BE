package com.example.Premind_BE.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDto {
    @Schema(description = "Access Token (JWT)", example = "추후 다른 요청들에서 인증을 위해 필요함")
    private String accessToken;
    @Schema(description = "Refresh Token (JWT)", example = "액세스 토큰 만료시 토큰 재발급을 위한 토큰이므로 accessToken처럼 로컬에 저장해두어야 함")
    private String refreshToken;
}
