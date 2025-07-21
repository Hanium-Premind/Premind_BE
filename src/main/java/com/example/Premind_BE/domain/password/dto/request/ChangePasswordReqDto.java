package com.example.Premind_BE.domain.password.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "개인정보 페이지에서 새로운 비밀번호를 설정하기 위한 요청 DTO")
public class ChangePasswordReqDto {
    private String newPassword;
}
