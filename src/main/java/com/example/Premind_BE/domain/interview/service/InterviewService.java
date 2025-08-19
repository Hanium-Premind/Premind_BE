package com.example.Premind_BE.domain.interview.service;

import com.example.Premind_BE.domain.interview.dto.ai.response.PracticeQAFeedbackResDto;
import com.example.Premind_BE.domain.interview.dao.InterviewRecordRepository;
import com.example.Premind_BE.domain.interview.domain.InterviewModeType;
import com.example.Premind_BE.domain.interview.domain.InterviewRecord;
import com.example.Premind_BE.domain.interview.domain.InterviewerStyle;
import com.example.Premind_BE.domain.interview.dto.ai.request.StartPracticeItem;
import com.example.Premind_BE.domain.interview.dto.ai.request.StartPracticeRequest;
import com.example.Premind_BE.domain.interview.dto.request.PracticeQuestionsReqDto;
import com.example.Premind_BE.domain.interview.dto.request.PracticeSubmitReqDto;
import com.example.Premind_BE.domain.interview.dto.response.*;
import com.example.Premind_BE.domain.portfolio.dao.PortfolioRepository;
import com.example.Premind_BE.domain.portfolio.domain.Portfolio;
import com.example.Premind_BE.domain.resume.dao.ResumeRepository;
import com.example.Premind_BE.domain.resume.dao.ResumeSectionRepository;
import com.example.Premind_BE.domain.resume.domain.Resume;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.global.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewService {
    private final ResumeRepository resumeRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserUtil userUtil;
    private final WebClient interviewApiClient;
    private final ResumeSectionRepository resumeSectionRepository;
    private final InterviewRecordRepository interviewRecordRepository;

    public List<ResumeResDto> resumeList() {
        return resumeRepository.findByUser(userUtil.getCurrentUser())
                .stream()
                .map(ResumeResDto::from)
                .toList();
    }

    public List<PortfolioResDto> portfolioList() {
        return portfolioRepository.findByUser(userUtil.getCurrentUser())
                .stream()
                .map(PortfolioResDto::from)
                .toList();
    }

    // 수정 예정 (연습 모드 첫 번째 질문 생성)
    public PracticeQuestionResDto practiceQuestions(PracticeQuestionsReqDto reqDto) {
        // 자소서 조회
        Resume resume = getResume(reqDto.getResumeId());

        // 포트폴리오 조회
        Portfolio portfolio = getPortfolio(reqDto.getPortfolioId());

        // InterviewRecord 생성
        InterviewRecord interviewRecord = InterviewRecord.builder()
                // .jobMajor()
                // .jobMiddle()
                //  .jobMinor()
                .resume(resume)
                .portfolio(portfolio)
                .user(userUtil.getCurrentUser())
                .interviewModeType(InterviewModeType.PRACTICE)
                .questionNum(reqDto.getNumQuestions())
                .interviewerStyle(InterviewerStyle.valueOf(reqDto.getInterviewerStyle()))
                .build();

        // InterviewRecordType 생성 (나중에)
        interviewRecordRepository.save(interviewRecord);

        // resumeSection -> item
        List<StartPracticeItem> items = resumeSectionRepository.findByResume(resume);

        // AI에 면접 질문 생성 요청 보내기
        StartPracticeRequest apiRequest = StartPracticeRequest.builder()
                .items(items)
                .style(styleToString(reqDto.getInterviewerStyle()))
                .num_questions(reqDto.getNumQuestions())
                .file_name(resume.getTitle())
                .role_name(resume.getJobMinor().getName())
                .company_name(resume.getCompany())
                .build();

        GenerateQAResDto resDto = startPractice(apiRequest);

        // InterviewQA 생성???? - 나중에 고민 (답변이랑 한번에 저장할지...)

        return PracticeQuestionResDto
                .builder()
                .interview_record_id(interviewRecord.getId())
                .job_id(resDto.getJob_id())
                .question(resDto.getQuestion())
                .sequence(resDto.getTurn())
                .total_question_num(resDto.getTotal_turns())
                .build();
    }

    public GenerateQAResDto startPractice(StartPracticeRequest req) {
        return interviewApiClient.post()
                .uri("/api/v1/practice/start")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .retrieve()
                .onStatus(s -> s.is4xxClientError() || s.is5xxServerError(),
                        resp -> resp.bodyToMono(String.class)
                                .defaultIfEmpty("Unknown error")
                                .map(msg -> new RuntimeException("Interview API error: " + msg)))
                .bodyToMono(GenerateQAResDto.class)
                .block(); // 동기 방식
    }

    private String styleToString(String interviewerStyle) {
        return switch (interviewerStyle) {
            case "STRICT" -> "깐깐한";
            case "KIND" -> "상냥한";
            case "JOB_SPECIFIC" -> "실무중심";
            default -> "실무중심";
        };
    }

    private Portfolio getPortfolio(Long portfolioId) {
        Portfolio portfolio = null;
        if (portfolioId != null) {
            portfolio = portfolioRepository.findById(portfolioId)
                    .orElseThrow(() -> new CustomException(ErrorCode.PORTFOLIO_NOT_EXIST));
            userUtil.verifyPortfolioUser(portfolio);
        }
        return portfolio;
    }

    private Resume getResume(Long resumeId) {
        Resume resume = null;
        if (resumeId != null) {
            resume = resumeRepository.findById(resumeId)
                    .orElseThrow(() -> new CustomException(ErrorCode.RESUME_NOT_EXIST));
            userUtil.verifyResumeUser(resume);
        }
        return resume;
    }


    public PracticeQAFeedbackResDto questionSubmit(PracticeSubmitReqDto reqDto, Long interviewRecordId) {
        return uploadVideo(reqDto.getJob_id(), reqDto.getFile()); // 응답을 받으면 질문-답변 -> InterviewQA에 저장, 질문-답변 피드백 -> QAFeedback에 저장
    }

    public PracticeQAFeedbackResDto uploadVideo(String jobId, MultipartFile videoFile) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("job_id", jobId);
        builder.part("file", videoFile.getResource())
                .filename(videoFile.getOriginalFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM);

        return interviewApiClient.post()
                .uri("/api/v1/practice/submit")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .onStatus(s -> s.is4xxClientError() || s.is5xxServerError(),
                        resp -> resp.bodyToMono(String.class)
                                .defaultIfEmpty("Unknown error")
                                .map(msg -> new RuntimeException("Interview API error: " + msg)))
                .bodyToMono(PracticeQAFeedbackResDto.class)
                .block();
    }
}
