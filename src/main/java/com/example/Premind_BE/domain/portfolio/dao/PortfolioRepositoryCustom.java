package com.example.Premind_BE.domain.portfolio.dao;

import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioInquiryResDto;
import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioListResDto;
import com.example.Premind_BE.domain.user.domain.User;

import java.util.List;

public interface PortfolioRepositoryCustom {
    PortfolioInquiryResDto findPortfolioInquiry(Long portfolioId);
    List<PortfolioListResDto> findAllPortfolioListWithJobMinor(User currentUser);
}
