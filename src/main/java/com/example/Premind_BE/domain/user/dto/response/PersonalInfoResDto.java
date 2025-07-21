package com.example.Premind_BE.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "마이페이지에서 개인 정보 조회 응답 dto")
public class PersonalInfoResDto {
    @Schema(description = "사용자 이름")
    private String name;
    @Schema(description = "사용자 생년월일")
    private String birth;
    @Schema(description = "사용자 이메일")
    private String email;
}
