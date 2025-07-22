package com.example.Premind_BE.domain.resume.service;

import com.example.Premind_BE.domain.job.dao.JobCategoryRepository;
import com.example.Premind_BE.domain.job.domain.Level;
import com.example.Premind_BE.domain.resume.dao.ResumeRepository;
import com.example.Premind_BE.domain.resume.dao.ResumeSectionRepository;
import com.example.Premind_BE.domain.resume.domain.Resume;
import com.example.Premind_BE.domain.resume.domain.ResumeSection;
import com.example.Premind_BE.domain.resume.dto.request.ResumeUploadReqDto;
import com.example.Premind_BE.domain.resume.dto.request.Section;
import com.example.Premind_BE.domain.user.dao.UserRepository;
import com.example.Premind_BE.domain.user.domain.User;
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
                .jobMajor(jobCategoryRepository.findByLevelAndId(Level.MAJOR, resumeUploadDto.getJobMajorId()))
                .jobMiddle(jobCategoryRepository.findByLevelAndId(Level.MIDDLE, resumeUploadDto.getJobMiddleId()))
                .jobMinor(jobCategoryRepository.findByLevelAndId(Level.MINOR, resumeUploadDto.getJobMinorId()))
                .title(resumeUploadDto.getTitle())
                .memo(resumeUploadDto.getMemo())
                .company(resumeUploadDto.getCompany())
                .createdDate(LocalDateTime.now())
                .build();
        resumeRepository.save(resume);

        // 질문-답변 리스트 저장
        List<Section> sectionList = resumeUploadDto.getQaList();
        for (int i = 0; i < sectionList.size(); i++) {
            Section section = sectionList.get(i);
            ResumeSection resumeSection = ResumeSection.builder()
                    .resume(resume)
                    .sequence(i + 1)
                    .question(section.getQuestion())
                    .answer(section.getAnswer())
                    .build();
            resumeSectionRepository.save(resumeSection);
        }

        return resumeUploadDto;
    }

    private User getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // subject → email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
