package com.example.Premind_BE.domain.user.dto.request;

import com.example.Premind_BE.domain.user.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "개인 정보를 수정하기 위한 요청 DTO")
public class UpdatePersonalInfoReqDto {
     private String name;

     @Schema(description = "사용자 생년월일 YYYYMMDD 형식으로")
     @Pattern(regexp = "\\d{8}", message = "생년월일은 yyyyMMdd 형식의 8자리 숫자여야 합니다.")
     private String birth;

     @Schema(description = "사용자 성별, FEMALE, MALE 값으로 전송")
     private Gender gender;

     private String email;
     private String phoneNumber;
     private String username;

     public LocalDate getBirthAsLocalDate() {
          return LocalDate.parse(this.birth, DateTimeFormatter.ofPattern("yyyyMMdd"));
     }
}
