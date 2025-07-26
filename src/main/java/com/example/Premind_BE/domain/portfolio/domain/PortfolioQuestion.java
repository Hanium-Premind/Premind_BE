package com.example.Premind_BE.domain.portfolio.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "portfolio_questions")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_question_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;
}

