package com.example.Premind_BE.domain.portfolio.dao;

import com.example.Premind_BE.domain.job.domain.QJobCategory;
import com.example.Premind_BE.domain.portfolio.domain.QPortfolio;
import com.example.Premind_BE.domain.portfolio.domain.QPortfolioSection;
import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioInquiryResDto;
import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioListResDto;
import com.example.Premind_BE.domain.portfolio.dto.response.PortfolioSectionResDto;
import com.example.Premind_BE.domain.resume.dto.response.ResumeSectionDto;
import com.example.Premind_BE.domain.user.domain.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PortfolioRepositoryCustomImpl implements PortfolioRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public PortfolioInquiryResDto findPortfolioInquiry(Long portfolioId) {
        QPortfolio portfolio = QPortfolio.portfolio;
        QJobCategory major = new QJobCategory("major");
        QJobCategory middle = new QJobCategory("middle");
        QJobCategory minor = new QJobCategory("minor");
        QPortfolioSection section = QPortfolioSection.portfolioSection;

        // 1. Portfolio 기본 정보 조회
        PortfolioInquiryResDto portfolioDto = queryFactory
                .select(Projections.constructor(PortfolioInquiryResDto.class,
                        portfolio.id,
                        major.id,
                        major.name,
                        middle.id,
                        middle.name,
                        minor.id,
                        minor.name,
                        portfolio.title,
                        portfolio.company,
                        portfolio.filePath,
                        portfolio.createdDate
                ))
                .from(portfolio)
                .join(portfolio.jobMajor, major)
                .join(portfolio.jobMiddle, middle)
                .join(portfolio.jobMinor, minor)
                .where(portfolio.id.eq(portfolioId))
                .fetchOne();

        if (portfolioDto == null) {
            return null;
        }

        // ResumeSection 리스트 조회 후 매핑
        List<PortfolioSectionResDto> sections = queryFactory
                .select(Projections.constructor(PortfolioSectionResDto.class,
                        section.id,
                        section.sequence,
                        section.question,
                        section.answer
                ))
                .from(section)
                .where(section.portfolio.id.eq(portfolioId))
                .orderBy(section.sequence.asc())
                .fetch();

        portfolioDto.setSections(sections);
        return portfolioDto;
    }

    @Override
    public List<PortfolioListResDto> findAllPortfolioListWithJobMinor(User currentUser) {
        QPortfolio portfolio = QPortfolio.portfolio;
        QJobCategory jobMinor = QJobCategory.jobCategory;

        return queryFactory
                .select(Projections.constructor(PortfolioListResDto.class,
                        portfolio.id,
                        portfolio.title,
                        jobMinor.name,
                        portfolio.company,
                        portfolio.createdDate
                ))
                .from(portfolio)
                .join(portfolio.jobMinor, jobMinor)
                .where(portfolio.user.eq(currentUser))
                .orderBy(portfolio.createdDate.desc())
                .fetch();
    }
}
