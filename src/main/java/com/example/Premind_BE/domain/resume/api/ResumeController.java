package com.example.Premind_BE.domain.resume.api;

import com.example.Premind_BE.domain.resume.dto.response.ResumeInquiryResDto;
import com.example.Premind_BE.domain.resume.dto.request.ResumeUploadReqDto;
import com.example.Premind_BE.domain.resume.dto.response.ResumeListResDto;
import com.example.Premind_BE.domain.resume.service.ResumeService;
import com.example.Premind_BE.global.common.response.MessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "자소서 목록 조회 API")
    @GetMapping("/list")
    public List<ResumeListResDto> resumeList() {
        return resumeService.resumeList();
    }

    @Operation(summary = "자소서 상세 조회 API")
    @Parameter(name = "resumeId", in = ParameterIn.PATH, description = "조회하고자 하는 자소서 id값", required = true)
    @GetMapping("/{resumeId}")
    public ResumeInquiryResDto resumeInquiry(@PathVariable Long resumeId) {
        return resumeService.resumeInquiry(resumeId);
    }

    @Operation(summary = "자소서 삭제 API")
    @Parameter(name = "resumeId", in = ParameterIn.PATH, description = "삭제하고자 하는 자소서 id값", required = true)
    @DeleteMapping("/{resumeId}")
    public MessageDto deleteResume(@PathVariable Long resumeId) {
        return resumeService.deleteResume(resumeId);
    }
}
