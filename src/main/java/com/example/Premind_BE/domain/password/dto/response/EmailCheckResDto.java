package com.example.Premind_BE.domain.password.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "비밀번호 찾기시 이메일 확인 응답 DTO")
public class EmailCheckResDto {
    private String message;
}