package com.example.Premind_BE.domain.interview.api;


import com.example.Premind_BE.domain.interview.dto.request.GenerateQAReqDto;
import com.example.Premind_BE.domain.interview.dto.response.GenerateQAResDto;
import com.example.Premind_BE.domain.interview.dto.response.PortfolioResDto;
import com.example.Premind_BE.domain.interview.dto.response.ResumeResDto;
import com.example.Premind_BE.domain.interview.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/interviews")
@RequiredArgsConstructor
@Tag(name = "Interview API", description = "면접 관련 API입니다.")
public class InterviewController {
    private final InterviewService interviewService;

    @Operation(summary = "면접 옵션 선택에서 자소서 목록 조회")
    @GetMapping(value = "/resumes")
    public List<ResumeResDto> resumeList() {
        return interviewService.resumeList();
    }

    @Operation(summary = "면접 옵션 선택에서 포트폴리오 목록 조회")
    @GetMapping(value = "/portfolios")
    public List<PortfolioResDto> portfolioList() {
        return interviewService.portfolioList();
    }

    @Operation(summary = "면접 옵션을 기반으로 면접 질문 생성하기")
    @GetMapping(value = "/generate/questions")
    public List<GenerateQAResDto> generateInterviewQA(@RequestBody GenerateQAReqDto reqDto) {
        return interviewService.generateInterviewQA(reqDto);
    }
}
