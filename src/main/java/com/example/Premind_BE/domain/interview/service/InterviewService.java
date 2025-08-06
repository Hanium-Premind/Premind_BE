package com.example.Premind_BE.domain.interview.service;

import com.example.Premind_BE.domain.interview.dto.response.PortfolioResDto;
import com.example.Premind_BE.domain.interview.dto.response.ResumeResDto;
import com.example.Premind_BE.domain.portfolio.dao.PortfolioRepository;
import com.example.Premind_BE.domain.resume.dao.ResumeRepository;
import com.example.Premind_BE.global.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterviewService {
    private final ResumeRepository resumeRepository;
    private final PortfolioRepository portfolioRepository;
    private final UserUtil userUtil;

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
}
