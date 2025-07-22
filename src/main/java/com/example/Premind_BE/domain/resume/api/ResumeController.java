package com.example.Premind_BE.domain.resume.api;

import com.example.Premind_BE.domain.resume.dto.request.ResumeUploadReqDto;
import com.example.Premind_BE.domain.resume.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
@Tag(name = "Resume API", description = "자기소개서 관련 API입니다.")
public class ResumeController {
    private final ResumeService resumeService;

    @Operation(summary = "자소서 업로드 API")
    @PostMapping("/upload")
    public ResumeUploadReqDto uploadResume(@RequestBody ResumeUploadReqDto resumeUploadDto) {
        return resumeService.uploadResume(resumeUploadDto);
    }


}
