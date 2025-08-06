package com.example.Premind_BE.domain.portfolio.dao;

import com.example.Premind_BE.domain.portfolio.domain.PortfolioQuestion;
import org.springframework.data.repository.CrudRepository;

public interface PortfolioQuestionRedisRepository extends CrudRepository<PortfolioQuestion, Long> {
}
