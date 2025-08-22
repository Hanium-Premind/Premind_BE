package com.example.Premind_BE.domain.resume.dao;

import com.example.Premind_BE.domain.job.domain.QJobCategory;
import com.example.Premind_BE.domain.resume.domain.QResume;
import com.example.Premind_BE.domain.resume.domain.QResumeSection;
import com.example.Premind_BE.domain.resume.dto.response.ResumeInquiryResDto;
import com.example.Premind_BE.domain.resume.dto.response.ResumeListResDto;
import com.example.Premind_BE.domain.resume.dto.response.ResumeSectionDto;
import com.example.Premind_BE.domain.user.domain.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResumeRepositoryCustomImpl implements ResumeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ResumeListResDto> findAllResumeListWithJobMinor(User currentUser) {
        QResume resume = QResume.resume;
        QJobCategory jobMinor = QJobCategory.jobCategory;

        return queryFactory
                .select(Projections.constructor(ResumeListResDto.class,
                        resume.id,
                        resume.title,
                        resume.memo,
                        jobMinor.name,
                        resume.company,
                        resume.createdDate
                ))
                .from(resume)
                .join(resume.jobMinor, jobMinor)
                .where(resume.user.eq(currentUser))
                .orderBy(resume.createdDate.desc())
                .fetch();
    }

    @Override
    public ResumeInquiryResDto findResumeInquiry(Long resumeId) {
        QResume resume = QResume.resume;
        QJobCategory major = new QJobCategory("major");
        QJobCategory middle = new QJobCategory("middle");
        QJobCategory minor = new QJobCategory("minor");
        QResumeSection section = QResumeSection.resumeSection;

        // 1. Resume 기본 정보 조회
        ResumeInquiryResDto resumeDto = queryFactory
                .select(Projections.constructor(ResumeInquiryResDto.class,
                        resume.id,
                        major.id,
                        major.name,
                        middle.id,
                        middle.name,
                        minor.id,
                        minor.name,
                        resume.title,
                        resume.memo,
                        resume.company,
                        resume.createdDate
                ))
                .from(resume)
                .join(resume.jobMajor, major)
                .join(resume.jobMiddle, middle)
                .join(resume.jobMinor, minor)
                .where(resume.id.eq(resumeId))
                .fetchOne();

        if (resumeDto == null) {
            return null;
        }

        // ResumeSection 리스트 조회 후 매핑
        List<ResumeSectionDto> sections = queryFactory
                .select(Projections.constructor(ResumeSectionDto.class,
                        section.id,
                        section.sequence,
                        section.question,
                        section.answer
                ))
                .from(section)
                .where(section.resume.id.eq(resumeId))
                .orderBy(section.sequence.asc())
                .fetch();

        resumeDto.setSections(sections);
        return resumeDto;
    }

}
