package com.example.Premind_BE.domain.interview.service;

import com.example.Premind_BE.domain.interview.dto.request.GenerateQAReqDto;
import com.example.Premind_BE.domain.interview.dto.response.GenerateQAResDto;
import com.example.Premind_BE.domain.interview.dto.response.PortfolioResDto;
import com.example.Premind_BE.domain.interview.dto.response.ResumeResDto;
import com.example.Premind_BE.domain.portfolio.dao.PortfolioRepository;
import com.example.Premind_BE.domain.portfolio.domain.Portfolio;
import com.example.Premind_BE.domain.resume.dao.ResumeRepository;
import com.example.Premind_BE.domain.resume.domain.Resume;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.global.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewService {
    private final ResumeRepository resumeRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserUtil userUtil;
    private final WebClient webClient;

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

    public List<GenerateQAResDto> generateInterviewQA(GenerateQAReqDto reqDto) {
        if(reqDto.getResumeId()!=null) {
            Resume resume = resumeRepository.findById(reqDto.getResumeId())
                    .orElseThrow(() -> new CustomException(ErrorCode.RESUME_NOT_EXIST));
        }

        if(reqDto.getPortfolioId()!=null) {
            Portfolio portfolio = portfolioRepository.findById(reqDto.getPortfolioId())
                    .orElseThrow(() -> new CustomException(ErrorCode.PORTFOLIO_NOT_EXIST));
        }
        
        /*return client.post()
                .uri("/api/v1/generate-questions") // 경로 직접 지정
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();*/

        return null;
    }
}
