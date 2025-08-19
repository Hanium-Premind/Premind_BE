package com.example.Premind_BE.domain.interview.api;


import com.example.Premind_BE.domain.interview.dto.ai.response.PracticeQAFeedbackResDto;
import com.example.Premind_BE.domain.interview.dto.request.PracticeQuestionsReqDto;
import com.example.Premind_BE.domain.interview.dto.request.PracticeSubmitReqDto;
import com.example.Premind_BE.domain.interview.dto.response.PortfolioResDto;
import com.example.Premind_BE.domain.interview.dto.response.PracticeQuestionResDto;
import com.example.Premind_BE.domain.interview.dto.response.PracticeSubmitResDto;
import com.example.Premind_BE.domain.interview.dto.response.ResumeResDto;
import com.example.Premind_BE.domain.interview.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "면접 옵션을 기반으로 연습모드 면접 질문 생성하기")
    @GetMapping(value = "/practice/questions")
    public PracticeQuestionResDto practiceQuestions(@RequestBody PracticeQuestionsReqDto reqDto) {
        return interviewService.practiceQuestions(reqDto);
    }

    @Operation(summary = "면접 질문에 대한 답변 제출하기 + 피드백 받기")
    @Parameter(name = "interviewRecordId", in = ParameterIn.PATH, description = "면접이 진행되고 있는 면접기록 id값", required = true)
    @GetMapping(value = "/practice/submit/{interviewRecordId}")
    @PostMapping(value = "/practice/submit/{interviewRecordId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PracticeQAFeedbackResDto questionSubmit(
            @ModelAttribute PracticeSubmitReqDto reqDto,
            @PathVariable Long interviewRecordId) {
        return interviewService.questionSubmit(reqDto, interviewRecordId);
    }
}
