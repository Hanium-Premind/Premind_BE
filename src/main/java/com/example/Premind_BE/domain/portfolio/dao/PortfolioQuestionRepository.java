package com.example.Premind_BE.domain.portfolio.dao;

import com.example.Premind_BE.domain.portfolio.domain.PortfolioQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioQuestionRepository extends JpaRepository<PortfolioQuestion, Long> {
}
