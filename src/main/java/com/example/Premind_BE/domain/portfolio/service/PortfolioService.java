package com.example.Premind_BE.domain.portfolio.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.example.Premind_BE.domain.job.dao.JobCategoryRepository;
import com.example.Premind_BE.domain.job.domain.Level;
import com.example.Premind_BE.domain.portfolio.dao.PortfolioQuestionRepository;
import com.example.Premind_BE.domain.portfolio.dao.PortfolioRepository;
import com.example.Premind_BE.domain.portfolio.domain.Portfolio;
import com.example.Premind_BE.domain.portfolio.domain.PortfolioSection;
import com.example.Premind_BE.domain.portfolio.dto.response.PresignedUrlResDto;
import com.example.Premind_BE.domain.portfolio.dto.request.PortfolioSectionReqDto;
import com.example.Premind_BE.domain.portfolio.dto.request.PortfolioUploadReqDto;
import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioQuestionResDto;
import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioUploadResDto;
import com.example.Premind_BE.domain.user.dao.UserRepository;
import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.infra.s3.S3Properties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final PortfolioQuestionRepository portfolioQuestionRepository;
    private final FileService fileService;
    private final S3Properties s3Properties;
    private final AmazonS3 amazonS3;

    public PresignedUrlResDto generatePresignedUrl() {
        Long memberId = getCurrentMember().getId();
        String fileKey = fileService.generateUUID();
        String fileName = fileService.createFileName(memberId, fileKey);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                fileService.createGeneratePresignedUrlRequest(s3Properties.getBucket(), fileName);

        String presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
        return new PresignedUrlResDto(presignedUrl, fileKey);
    }



    public PortfolioUploadResDto uploadPortfolio(PortfolioUploadReqDto reqDto) {

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
                .filePath(reqDto.getFileUrl())
                .createdDate(LocalDateTime.now())
                .build();

        log.info("Portfolio created with title: {}", portfolio.getTitle());

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

        portfolioRepository.save(portfolio);

        return new PortfolioUploadResDto(portfolio.getId());
    }

    private User getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // subject → email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public List<PortfolioQuestionResDto> portfolioQuestionList() {
        return portfolioQuestionRepository.findAll()
                .stream()
                .map(PortfolioQuestionResDto::from)
                .toList();
    }
}
