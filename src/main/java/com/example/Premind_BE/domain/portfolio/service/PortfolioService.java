package com.example.Premind_BE.domain.portfolio.service;

import com.example.Premind_BE.domain.job.dao.JobCategoryRepository;
import com.example.Premind_BE.domain.job.domain.Level;
import com.example.Premind_BE.domain.portfolio.dao.PortfolioRepository;
import com.example.Premind_BE.domain.portfolio.domain.Portfolio;
import com.example.Premind_BE.domain.portfolio.domain.PortfolioSection;
import com.example.Premind_BE.domain.portfolio.dto.request.PortfolioSectionReqDto;
import com.example.Premind_BE.domain.portfolio.dto.request.PortfolioUploadReqDto;
import com.example.Premind_BE.domain.user.dao.UserRepository;
import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final JobCategoryRepository jobCategoryRepository;

    public void uploadPortfolio(PortfolioUploadReqDto reqDto, MultipartFile file) {
        // Portfolio 객체 생성
        Portfolio portfolio = Portfolio.builder()
                .user(getCurrentMember())
                .jobMajor(jobCategoryRepository.findByIdAndLevel(reqDto.getJobMajorId(), Level.MAJOR)
                        .orElseThrow(() -> new CustomException(ErrorCode.JOB_CATEGORY_NOT_FOUND)))
                .jobMiddle(jobCategoryRepository.findByIdAndLevel(reqDto.getJobMiddleId(), Level.MIDDLE)
                        .orElseThrow(() -> new CustomException(ErrorCode.JOB_CATEGORY_NOT_FOUND)))
                .jobMinor(jobCategoryRepository.findByIdAndLevel(reqDto.getJobMinorId(), Level.MINOR)
                        .orElseThrow(() -> new CustomException(ErrorCode.JOB_CATEGORY_NOT_FOUND)))
                .title(reqDto.getTitle())
                .company(reqDto.getCompany())
                .createdDate(LocalDateTime.now())
                .build();

        // 질문-답변 리스트 연관관계 추가
        List<PortfolioSectionReqDto> sectionList = reqDto.getQaList();
        for (int i = 0; i < sectionList.size(); i++) {
            PortfolioSectionReqDto section = sectionList.get(i);
            PortfolioSection resumeSection = PortfolioSection.builder()
                    .sequence(i + 1)
                    .question(section.getQuestion())
                    .answer(section.getAnswer())
                    .build();
            portfolio.addSection(resumeSection);
        }

        // 파일 업로드 로직


        portfolioRepository.save(portfolio);
    }

    private User getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // subject → email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
