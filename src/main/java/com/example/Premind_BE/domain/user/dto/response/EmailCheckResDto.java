package com.example.Premind_BE.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "이메일 중복확인 관련 응답 DTO")
public class EmailCheckResDto {
    @Schema(description = "중복확인 메시지")
    private String message;
    @Schema(description = "true이면 사용가능함")
    private boolean isAvailable;
}
