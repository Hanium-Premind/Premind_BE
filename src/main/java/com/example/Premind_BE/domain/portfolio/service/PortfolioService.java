package com.example.Premind_BE.domain.portfolio.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.example.Premind_BE.domain.job.domain.JobCategory;
import com.example.Premind_BE.domain.job.service.JobService;
import com.example.Premind_BE.domain.portfolio.dao.PortfolioQuestionRedisRepository;
import com.example.Premind_BE.domain.portfolio.dao.PortfolioRepository;
import com.example.Premind_BE.domain.portfolio.domain.Portfolio;
import com.example.Premind_BE.domain.portfolio.domain.PortfolioQuestion;
import com.example.Premind_BE.domain.portfolio.domain.PortfolioSection;
import com.example.Premind_BE.domain.portfolio.dto.request.PortfolioSectionReqDto;
import com.example.Premind_BE.domain.portfolio.dto.request.PortfolioUpdateReqDto;
import com.example.Premind_BE.domain.portfolio.dto.request.PortfolioUploadReqDto;
import com.example.Premind_BE.domain.portfolio.dto.response.*;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.global.util.UserUtil;
import com.example.Premind_BE.infra.s3.S3Properties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioQuestionRedisRepository portfolioQuestionRepository;
    private final FileService fileService;
    private final S3Properties s3Properties;
    private final AmazonS3 amazonS3;
    private final JobService jobService;
    private final UserUtil userUtil;

    public PresignedUrlResDto generatePresignedUrl() {
        Long memberId = userUtil.getCurrentUser().getId();
        String fileKey = fileService.generateUUID();
        String fileName = fileService.createFileName(memberId, fileKey);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                fileService.createGeneratePresignedUrlRequest(s3Properties.getBucket(), fileName);

        String presignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
        return new PresignedUrlResDto(presignedUrl, fileKey);
    }

    public PortfolioUploadResDto uploadPortfolio(PortfolioUploadReqDto reqDto) {
        List<JobCategory> jobCategories = jobService.findJobCategory(reqDto.getJobMajorId(), reqDto.getJobMiddleId(), reqDto.getJobMinorId());
        Portfolio portfolio = Portfolio.builder()
                .user(userUtil.getCurrentUser())
                .jobMajor(jobCategories.get(0))
                .jobMiddle(jobCategories.get(1))
                .jobMinor(jobCategories.get(2))
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

    public List<PortfolioQuestionResDto> portfolioQuestionList() {
        Iterable<PortfolioQuestion> all = portfolioQuestionRepository.findAll();

        return StreamSupport.stream(all.spliterator(),false)
                .map(PortfolioQuestionResDto::from)
                .toList();
    }

    public PortfolioInquiryResDto portfolioInquiry(Long portfolioId) {
        // portfolioId로 포트폴리오 조회
        Portfolio portfolio = findPortfolio(portfolioId);
        userUtil.verifyPortfolioUser(portfolio);

        return portfolioRepository.findPortfolioInquiry(portfolioId);
    }

    private Portfolio findPortfolio(Long portfolioId) {
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new CustomException(ErrorCode.PORTFOLIO_NOT_EXIST));
    }

    public void updatePortfolio(Long portfolioId, PortfolioUpdateReqDto dto) {
        Portfolio portfolio = findPortfolio(portfolioId);
        userUtil.verifyPortfolioUser(portfolio);

        // 기존 파일 삭제
        String existingFilePath = portfolio.getFilePath();
        if (dto.getFileUrl() != null && !dto.getFileUrl().equals(existingFilePath)) {
            fileService.deleteFile(existingFilePath); // fileUrl에서 key 추출해서 삭제
        }

        List<JobCategory> jobCategories = jobService.findJobCategory(dto.getJobMajorId(), dto.getJobMiddleId(), dto.getJobMinorId());

        portfolio.update(jobCategories.get(0), jobCategories.get(1), jobCategories.get(2), dto);
    }

    public void deletePortfolio(Long portfolioId) {
        Portfolio portfolio = findPortfolio(portfolioId);
        userUtil.verifyPortfolioUser(portfolio);

        try {
            fileService.deleteFile(portfolio.getFilePath());
        } catch (Exception e) {
            log.warn("Failed to delete S3 file: {}", portfolio.getFilePath(), e);
        }

        portfolioRepository.delete(portfolio);
    }

    public List<PortfolioListResDto> portfolioList() {
        return portfolioRepository.findAllPortfolioListWithJobMinor(userUtil.getCurrentUser());
    }
}
