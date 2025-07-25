package com.example.Premind_BE.domain.password.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "인증번호 받기 요청 DTO")
public class ReceiveCodeReqDto {
    @Schema(description = "회원가입시 입력한 사용자 이름")
    private String name;
    @Schema(description = "회원가입시 입력한 전화번호와 일치해야 한다.")
    @Pattern(regexp = "^010\\d{8}$", message = "전화번호는 하이픈 없이 010으로 시작하는 11자리 숫자여야 합니다.")
    private String phoneNumber;
}
