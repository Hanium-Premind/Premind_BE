package com.example.Premind_BE.domain.password.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String phoneNumber;
}
