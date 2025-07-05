package com.example.Premind_BE.domain.password.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "인증번호 검증하기 관련 요청 DTO")
public class VerifyCodeReqDto {
    @Schema(description = "인증번호를 받은 전화번호")
    private String phoneNumber;
    @Schema(description = "전송받은 인증번호")
    private String code;
}
