package com.example.Premind_BE.domain.password.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "인증번호 응답 요청 DTO")
public class ReceiveCodeResDto {
    private String message;
}
