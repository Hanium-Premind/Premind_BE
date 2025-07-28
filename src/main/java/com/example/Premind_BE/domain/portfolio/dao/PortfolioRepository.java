package com.example.Premind_BE.domain.portfolio.dao;

import com.example.Premind_BE.domain.portfolio.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>, PortfolioRepositoryCustom {
}
