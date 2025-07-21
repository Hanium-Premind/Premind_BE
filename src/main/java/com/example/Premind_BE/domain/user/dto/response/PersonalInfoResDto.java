package com.example.Premind_BE.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "개인정보 수정시 기존 개인 정보 조회 응답 DTO")
public class PersonalInfoResDto {
    @Schema(description = "사용자 이메일 (변경 불가)")
    private String email;
    @Schema(description = "기존 사용자 이름")
    private String name;
    @Schema(description = "기존 사용자 생년월일")
    private String birth;
    @Schema(description = "기존 사용자 성별")
    private String gender;
    @Schema(description = "기존 사용자 전화번호 (변경 불가)")
    private String phoneNumber;
    @Schema(description = "기존 사용자 관심 직무")
    private List<String> interestJobs;
}
