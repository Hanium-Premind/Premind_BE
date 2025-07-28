package com.example.Premind_BE.domain.portfolio.domain;

import com.example.Premind_BE.domain.job.domain.JobCategory;
import com.example.Premind_BE.domain.portfolio.dto.request.PortfolioSectionReqDto;
import com.example.Premind_BE.domain.portfolio.dto.request.PortfolioUpdateReqDto;
import com.example.Premind_BE.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "portfolio")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 직무 대분류
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_major_id")
    private JobCategory jobMajor;

    // 직무 중분류
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_middle_id")
    private JobCategory jobMiddle;

    // 직무 소분류
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_minor_id")
    private JobCategory jobMinor;

    @Column(length = 20)
    private String title;

    @Column(length = 20)
    private String company;

    @Column(length = 255)
    private String filePath;

    private LocalDateTime createdDate;

    @Builder.Default
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioSection> sections = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addSection(PortfolioSection section) {
        this.sections.add(section);
        section.setPortfolio(this);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void update(JobCategory major, JobCategory middle, JobCategory minor, PortfolioUpdateReqDto dto) {
        this.jobMajor = major;
        this.jobMiddle = middle;
        this.jobMinor = minor;
        this.title = dto.getTitle();
        this.company = dto.getCompany();
        this.filePath = dto.getFileUrl();

        // 기존 qa리스트 삭제 후 새로운 qa리스트로 업데이트
        this.sections.clear();

        int sq = 1;
        for (PortfolioSectionReqDto sec : dto.getQaList()) {
            PortfolioSection section = PortfolioSection.builder()
                    .portfolio(this)
                    .sequence(sq++)
                    .question(sec.getQuestion())
                    .answer(sec.getAnswer())
                    .build();
            this.addSection(section);
        }
    }
}
