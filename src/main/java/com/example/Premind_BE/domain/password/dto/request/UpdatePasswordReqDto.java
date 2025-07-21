package com.example.Premind_BE.domain.password.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "새로운 비밀번호를 설정하기 위한 요청 dto")
public class UpdatePasswordReqDto {
    @Schema(description = "비밀번호를 변경하고자하는 사용자의 이메일 (이메일 확인하기에서 입력받았던 이메일 값을 그대로 요청에 사용)")
    private String email;
    @Schema(description = "변경하고자하는 새로운 비밀번호")
    private String newPassword;
}
