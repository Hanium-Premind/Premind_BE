package com.example.Premind_BE.domain.portfolio.domain;

import com.example.Premind_BE.domain.resume.domain.Resume;
import jakarta.persistence.*;
import lombok.*;

import javax.sound.sampled.Port;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Table(name = "portfolio_sections")
public class PortfolioSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_section_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    private int sequence;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

}
