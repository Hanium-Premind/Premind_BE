package com.example.Premind_BE.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로그인 요청을 위한 DTO")
public class LoginReqDto {
    @Schema(description = "사용자 이메일")
    private String email;
    @Schema(description = "사용자 비밀번호")
    private String password;
}
