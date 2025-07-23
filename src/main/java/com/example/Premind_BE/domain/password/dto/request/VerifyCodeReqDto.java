package com.example.Premind_BE.domain.password.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "인증번호 검증하기 관련 요청 DTO")
public class VerifyCodeReqDto {

    @Schema(example = "01012345678", description = "010으로 시작하는 11자리 숫자")
    @Pattern(regexp = "^010\\d{8}$", message = "전화번호는 하이픈 없이 010으로 시작하는 11자리 숫자여야 합니다.")
    private String phoneNumber;

    @Schema(description = "전송받은 인증번호")
    private String code;
}
