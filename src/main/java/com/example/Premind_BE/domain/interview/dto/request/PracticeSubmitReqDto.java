package com.example.Premind_BE.domain.interview.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "연습모드에서 질문에 대한 답변을 하여 피드백 요청 DTO")
public class PracticeSubmitReqDto {
    @Schema(description = "AI 세션 ID (질문과 함께 받은 세션 id값)")
    private String job_id;

    @Schema(description = "사용자의 답변 영상 파일")
    private MultipartFile file;
}
