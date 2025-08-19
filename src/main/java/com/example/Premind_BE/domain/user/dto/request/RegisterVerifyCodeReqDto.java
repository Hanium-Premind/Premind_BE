package com.example.Premind_BE.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원가입 페이지에서 인증번호 검증을 위한 요청 DTO")
public class RegisterVerifyCodeReqDto {
    @Schema(description = "인증을 진행한 전화번호 ")
    @Pattern(regexp = "^010\\d{8}$", message = "전화번호는 하이픈 없이 010으로 시작하는 11자리 숫자여야 합니다.")
    private String phoneNumber;
    @Schema(description = "메시지로 받은 인증번호")
    private String code;
}
