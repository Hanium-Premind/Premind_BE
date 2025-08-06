package com.example.Premind_BE.domain.resume.service;

import com.example.Premind_BE.domain.job.domain.JobCategory;
import com.example.Premind_BE.domain.job.service.JobService;
import com.example.Premind_BE.domain.resume.domain.ResumeQuestion;
import com.example.Premind_BE.domain.resume.dto.response.ResumeQuestionResDto;
import com.example.Premind_BE.domain.resume.dao.ResumeQuestionRedisRepository;
import com.example.Premind_BE.domain.resume.dao.ResumeRepository;
import com.example.Premind_BE.domain.resume.domain.Resume;
import com.example.Premind_BE.domain.resume.domain.ResumeSection;
import com.example.Premind_BE.domain.resume.dto.ResumeUpdateDto;
import com.example.Premind_BE.domain.resume.dto.ResumeUploadDto;
import com.example.Premind_BE.domain.resume.dto.request.ResumeSectionReqDto;
import com.example.Premind_BE.domain.resume.dto.response.ResumeInquiryResDto;
import com.example.Premind_BE.domain.resume.dto.response.ResumeListResDto;
import com.example.Premind_BE.global.common.response.MessageDto;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.global.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final ResumeQuestionRedisRepository resumeQuestionRepository;
    private final JobService jobService;
    private final UserUtil userUtil;

    public ResumeUploadDto uploadResume(ResumeUploadDto resumeUploadDto) {
        List<JobCategory> jobCategories = jobService.findJobCategory(resumeUploadDto.getJobMajorId(), resumeUploadDto.getJobMiddleId(), resumeUploadDto.getJobMinorId());

        Resume resume = Resume.builder()
                .user(userUtil.getCurrentUser())
                .jobMajor(jobCategories.get(0))
                .jobMiddle(jobCategories.get(1))
                .jobMinor(jobCategories.get(2))
                .title(resumeUploadDto.getTitle())
                .memo(resumeUploadDto.getMemo())
                .company(resumeUploadDto.getCompany())
                .createdDate(LocalDateTime.now())
                .build();

        // 질문-답변 리스트 연관관계 추가
        List<ResumeSectionReqDto> sectionList = resumeUploadDto.getQaList();
        for (int i = 0; i < sectionList.size(); i++) {
            ResumeSectionReqDto section = sectionList.get(i);
            ResumeSection resumeSection = ResumeSection.builder()
                    .sequence(i + 1)
                    .question(section.getQuestion())
                    .answer(section.getAnswer())
                    .characterCount(section.getCharacterCount())
                    .build();
            resume.addSection(resumeSection); // 연관관계 설정
        }

        resumeRepository.save(resume);

        return resumeUploadDto;
    }

    public List<ResumeListResDto> resumeList() {
        return resumeRepository.findAllResumeListWithJobMinor(userUtil.getCurrentUser());
    }

    public ResumeInquiryResDto resumeInquiry(Long resumeId) {
        // 조회하고자하는 자소서의 작성자가 아니라면
        Resume resume = findResume(resumeId);
        verifyUser(resume); // 사용자 검증

        return resumeRepository.findResumeInquiry(resumeId);
    }

    public void updateResume(Long resumeId, ResumeUpdateDto dto) {
        Resume resume = findResume(resumeId);
        verifyUser(resume); // 사용자 검증

        List<JobCategory> jobCategories = jobService.findJobCategory(dto.getJobMajorId(), dto.getJobMiddleId(), dto.getJobMinorId());

        resume.update(jobCategories.get(0), jobCategories.get(1), jobCategories.get(2), dto);
    }

    public MessageDto deleteResume(Long resumeId) {
        Resume resume = findResume(resumeId);
        verifyUser(resume);

        resumeRepository.delete(resume);
        return new MessageDto(resumeId + "번 자기소개서가 삭제되었습니다.");
    }

    private void verifyUser(Resume resume) {
        if (!resume.getUser().equals(userUtil.getCurrentUser())) {
            throw new CustomException(ErrorCode.RESUME_ACCESS_DENIED);
        }
    }

    private Resume findResume(Long resumeId) {
        return resumeRepository.findById(resumeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESUME_NOT_EXIST));
    }

    public List<ResumeQuestionResDto> resumeQuestionList() {
        Iterable<ResumeQuestion> all = resumeQuestionRepository.findAll();

        // Iterable을 Stream으로 변환
        return StreamSupport.stream(all.spliterator(), false)
                .map(ResumeQuestionResDto::from)
                .toList();
    }
}
