package com.example.Premind_BE.domain.resume.service;

import com.example.Premind_BE.domain.job.dao.JobCategoryRepository;
import com.example.Premind_BE.domain.job.domain.Level;
import com.example.Premind_BE.domain.resume.dao.ResumeRepository;
import com.example.Premind_BE.domain.resume.dao.ResumeSectionRepository;
import com.example.Premind_BE.domain.resume.domain.Resume;
import com.example.Premind_BE.domain.resume.domain.ResumeSection;
import com.example.Premind_BE.domain.resume.dto.request.ResumeUploadReqDto;
import com.example.Premind_BE.domain.resume.dto.request.Section;
import com.example.Premind_BE.domain.resume.dto.response.ResumeInquiryResDto;
import com.example.Premind_BE.domain.resume.dto.response.ResumeListResDto;
import com.example.Premind_BE.domain.user.dao.UserRepository;
import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.global.common.response.MessageDto;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final ResumeSectionRepository resumeSectionRepository;

    public ResumeUploadReqDto uploadResume(ResumeUploadReqDto resumeUploadDto) {
        Resume resume = Resume.builder()
                .user(getCurrentMember())
                .jobMajor(jobCategoryRepository.findByIdAndLevel(resumeUploadDto.getJobMajorId(), Level.MAJOR)
                        .orElseThrow(() -> new CustomException(ErrorCode.JOB_CATEGORY_NOT_FOUND)))
                .jobMiddle(jobCategoryRepository.findByIdAndLevel(resumeUploadDto.getJobMiddleId(), Level.MIDDLE)
                        .orElseThrow(() -> new CustomException(ErrorCode.JOB_CATEGORY_NOT_FOUND)))
                .jobMinor(jobCategoryRepository.findByIdAndLevel(resumeUploadDto.getJobMinorId(), Level.MINOR)
                        .orElseThrow(() -> new CustomException(ErrorCode.JOB_CATEGORY_NOT_FOUND)))
                .title(resumeUploadDto.getTitle())
                .memo(resumeUploadDto.getMemo())
                .company(resumeUploadDto.getCompany())
                .createdDate(LocalDateTime.now())
                .build();


        // 질문-답변 리스트 연관관계 추가
        List<Section> sectionList = resumeUploadDto.getQaList();
        for (int i = 0; i < sectionList.size(); i++) {
            Section section = sectionList.get(i);
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

    private User getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // subject → email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public List<ResumeListResDto> resumeList() {
        return resumeRepository.findAllResumeListWithJobMinor(getCurrentMember());
    }

    public ResumeInquiryResDto resumeInquiry(Long resumeId) {
        // 조회하고자하는 자소서의 작성자가 아니라면
        Resume resume = resumeRepository.findByIdAndUser(resumeId, getCurrentMember())
                .orElseThrow(() -> new CustomException(ErrorCode.RESUME_ACCESS_DENIED));

        return resumeRepository.findResumeInquiry(resumeId);
    }

    public MessageDto deleteResume(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESUME_NOT_EXIST));

        // 로그인 유저의 이력서인지 확인
        if (!resume.getUser().equals(getCurrentMember())) {
            throw new CustomException(ErrorCode.RESUME_ACCESS_DENIED);
        }

        resumeRepository.delete(resume);
        return new MessageDto(resumeId + "번 자기소개서가 삭제되었습니다.");
    }

}
