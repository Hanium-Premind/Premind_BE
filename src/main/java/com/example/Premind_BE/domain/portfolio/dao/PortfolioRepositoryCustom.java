package com.example.Premind_BE.domain.portfolio.dao;

import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioInquiryResDto;

public interface PortfolioRepositoryCustom {
    PortfolioInquiryResDto findPortfolioInquiry(Long portfolioId);
}
