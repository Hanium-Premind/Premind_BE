package com.example.Premind_BE.domain.user.dto.request;

import com.example.Premind_BE.domain.user.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "사용자 회원가입 요청 관련 DTO")
public class RegisterReqDto {
    @Schema(description = "사용자 아이디")
    private String username;
    @Schema(description = "사용자 비밀번호")
    private String password;
    @Schema(description = "사용자 이름")
    private String name;
    @Schema(description = "사용자 생년월일 YYYYMMDD 형식으로")
    private String birth;
    @Schema(description = "사용자 성별, FEMALE, MALE 값으로 전송")
    private Gender gender;
    @Schema(description = "사용자 전화번호, 01012345678 형식으로")
    private String  phoneNumber;

    public LocalDate getBirthAsLocalDate() {
        return LocalDate.parse(this.birth, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
