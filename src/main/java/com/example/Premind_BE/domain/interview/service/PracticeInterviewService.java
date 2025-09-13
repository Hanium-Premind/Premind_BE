package com.example.Premind_BE.domain.interview.service;

import com.example.Premind_BE.domain.interview.dao.InterviewQARepository;
import com.example.Premind_BE.domain.interview.dao.InterviewRecordRepository;
import com.example.Premind_BE.domain.interview.dao.TotalFeedbackRepository;
import com.example.Premind_BE.domain.interview.domain.*;
import com.example.Premind_BE.domain.interview.dto.response.PreviousRecordsResDto;
import com.example.Premind_BE.domain.interview.dto.ai.request.StartPracticeItem;
import com.example.Premind_BE.domain.interview.dto.ai.request.StartPracticeRequest;
import com.example.Premind_BE.domain.interview.dto.ai.response.*;
import com.example.Premind_BE.domain.interview.dto.request.PracticeQuestionsReqDto;
import com.example.Premind_BE.domain.interview.dto.request.PracticeSubmitReqDto;
import com.example.Premind_BE.domain.interview.dto.response.PortfolioResDto;
import com.example.Premind_BE.domain.interview.dto.response.PracticeQuestionResDto;
import com.example.Premind_BE.domain.interview.dto.response.PracticeSubmitResDto;
import com.example.Premind_BE.domain.interview.dto.response.ResumeResDto;
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
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PracticeInterviewService {
    private final ResumeRepository resumeRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserUtil userUtil;
    private final ResumeSectionRepository resumeSectionRepository;
    private final InterviewRecordRepository interviewRecordRepository;
    private final InterviewQARepository interviewQARepository;
    private final APIWebClient apiWebClient;
    private final TotalFeedbackRepository totalFeedbackRepository;

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
    public PracticeQuestionResDto practiceCreateQuestions(PracticeQuestionsReqDto reqDto) {
        // 1. 자소서 & 포트폴리오 조회
        Resume resume = getResume(reqDto.getResumeId());
        Portfolio portfolio = getPortfolio(reqDto.getPortfolioId());

        // 2. InterviewRecord 생성 및 저장
        InterviewRecord interviewRecord = createInterviewRecord(reqDto, resume, portfolio);

        // 3. AI API 요청 생성 및 호출
        GenerateQAResDto resDto = requestQuestionsFromAI(reqDto, resume, interviewRecord);

        // 4. 응답 DTO 반환
        return buildPracticeQuestionResDto(interviewRecord, resDto);
    }

    private InterviewRecord createInterviewRecord(PracticeQuestionsReqDto reqDto, Resume resume, Portfolio portfolio) {
        InterviewRecord interviewRecord = InterviewRecord.builder()
                .resume(resume)
                .portfolio(portfolio)
                .user(userUtil.getCurrentUser())
                .interviewModeType(InterviewModeType.PRACTICE)
                .questionNum(reqDto.getNumQuestions())
                .interviewerStyle(InterviewerStyle.valueOf(reqDto.getInterviewerStyle()))
                .createdDate(LocalDateTime.now())
                .build();

        return interviewRecordRepository.save(interviewRecord);
    }

    private GenerateQAResDto requestQuestionsFromAI(PracticeQuestionsReqDto reqDto, Resume resume, InterviewRecord interviewRecord) {
        List<StartPracticeItem> items = resumeSectionRepository.findByResume(resume);

        StartPracticeRequest apiRequest = StartPracticeRequest.builder()
                .items(items)
                .style(styleToString(reqDto.getInterviewerStyle()))
                .num_questions(reqDto.getNumQuestions())
                .file_name(resume.getTitle())
                .role_name(resume.getJobMinor().getName())
                .company_name(resume.getCompany())
                .build();

        GenerateQAResDto resDto = apiWebClient.startPractice(apiRequest);

        interviewRecord.putSessionId(resDto.getJob_id()); // 세션 ID 저장

        return resDto;
    }

    private PracticeQuestionResDto buildPracticeQuestionResDto(InterviewRecord interviewRecord, GenerateQAResDto resDto) {
        return PracticeQuestionResDto.builder()
                .interview_record_id(interviewRecord.getId())
                .job_id(resDto.getJob_id())
                .question(resDto.getQuestion())
                .sequence(resDto.getTurn())
                .total_question_num(resDto.getTotal_turns())
                .build();
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


    public PracticeSubmitResDto practiceQuestionSubmit(PracticeSubmitReqDto reqDto, Long interviewRecordId) {
        // 1. 영상 업로드 및 피드백 응답 받기
        PracticeQAFeedbackResDto resDto = apiWebClient.uploadVideo(reqDto.getJob_id(), reqDto.getFile());

        // 2. InterviewRecord 조회 및 검증
        InterviewRecord record = findAndVerifyInterviewRecord(interviewRecordId);

        // 3. InterviewQA 저장
        saveInterviewQA(record, reqDto, resDto);

        // 4. 마지막 응답이면 최종 리포트 저장
        PracticeTotalReportResDto report = resDto.isFinished()
                ? saveFinalReport(record, resDto)
                : null;

        // 5. 최종 응답 DTO 반환
        return buildPracticeSubmitResDto(reqDto, resDto, report);
    }

    private InterviewRecord findAndVerifyInterviewRecord(Long interviewRecordId) {
        InterviewRecord record = interviewRecordRepository.findById(interviewRecordId)
                .orElseThrow(() -> new CustomException(ErrorCode.INTERVIEW_RECORD_NOT_FOUND));
        userUtil.verifyInterviewReccordUser(record);
        return record;
    }

    private void saveInterviewQA(InterviewRecord record, PracticeSubmitReqDto reqDto, PracticeQAFeedbackResDto resDto) {
        InterviewQA interviewQA = InterviewQA.builder()
                .interviewRecord(record)
                .question(resDto.getQuestion())
                .answer(resDto.getAnswer_text())
                .sequence(resDto.getTurn())
                .answerTime(reqDto.getAnswer_time())
                .qaFeedback(resDto.getShort_feedback())
                .build();
        interviewQARepository.save(interviewQA);
    }

    private PracticeTotalReportResDto saveFinalReport(InterviewRecord record, PracticeQAFeedbackResDto resDto) {
        Double totalTime = getTotalTime(record.getCreatedDate(), LocalDateTime.now());
        PracticeTotalReportResDto r = resDto.getReport();

        // Report DTO 빌드
        PracticeTotalReportResDto report = PracticeTotalReportResDto.builder()
                .total_100(r.getTotal_100())
                .summary(r.getSummary())
                .voice(new VoiceSection(r.getVoice().getFluency_20(), r.getVoice().getSpeed_20(), r.getVoice().getTotal_40(), r.getVoice().getFeedback()))
                .gaze(new GazeSection(r.getGaze().getEye_10(), r.getGaze().getExpression_10(), r.getGaze().getTotal_20(), r.getGaze().getFeedback()))
                .content(new Content(r.getContent().getAppropriateness_40(), r.getContent().getFeedback()))
                .jobName(null) // TODO: 수정 예정
                .totalQuestion(resDto.getTotal_turns())
                .totalTime(totalTime)
                .build();

        // DB 저장
        totalFeedbackRepository.save(TotalFeedback.builder()
                .interviewRecord(record)
                .totalScore(r.getTotal_100())
                .summary(r.getSummary())
                .voiceFluency(r.getVoice().getFluency_20())
                .voiceSpeed(r.getVoice().getSpeed_20())
                .voiceTotal(r.getVoice().getTotal_40())
                .voiceFeedback(r.getVoice().getFeedback())
                .gazeEye(r.getGaze().getEye_10())
                .gazeExpression(r.getGaze().getExpression_10())
                .gazeTotal(r.getGaze().getTotal_20())
                .gazeFeedback(r.getGaze().getFeedback())
                .contentAppropriateness(r.getContent().getAppropriateness_40())
                .contentFeedback(r.getContent().getFeedback())
                .jobName(null) // TODO: 수정 예정
                .totalQuestion(resDto.getTotal_turns())
                .totalTime(totalTime)
                .build()
        );

        return report;
    }

    private PracticeSubmitResDto buildPracticeSubmitResDto(PracticeSubmitReqDto reqDto, PracticeQAFeedbackResDto resDto, PracticeTotalReportResDto report) {
        return PracticeSubmitResDto.builder()
                .job_id(reqDto.getJob_id())
                .sequence(resDto.getTurn())
                .total_question_num(resDto.getTotal_turns())
                .question(resDto.getQuestion())
                .short_feedback(resDto.getShort_feedback())
                .next_question(resDto.getNext_question())
                .finished(resDto.isFinished())
                .report(report)
                .build();
    }


    private Double getTotalTime(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return null;
        }
        // Duration으로 두 시간 차이를 계산
        Duration duration = Duration.between(start, end);
        return duration.toMillis() / 1000.0;
    }

    public List<PreviousRecordsResDto> previousRecords(String interviewMode) {
        InterviewModeType modeType = null;

        if (interviewMode != null && !interviewMode.isBlank()) {
            try {
                modeType = InterviewModeType.valueOf(interviewMode.toUpperCase());
                // PRACTICE / REAL / REVIEW 문자열을 enum으로 변환
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("❌ 지원하지 않는 면접 모드입니다: " + interviewMode);
            }
        }

        return interviewRecordRepository.findByAllRecord(modeType);
    }
}
