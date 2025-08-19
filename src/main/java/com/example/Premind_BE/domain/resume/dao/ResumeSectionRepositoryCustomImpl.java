package com.example.Premind_BE.domain.resume.dao;

import com.example.Premind_BE.domain.interview.dto.ai.request.StartPracticeItem;
import com.example.Premind_BE.domain.resume.domain.QResumeSection;
import com.example.Premind_BE.domain.resume.domain.Resume;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResumeSectionRepositoryCustomImpl implements ResumeSectionRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<StartPracticeItem> findByResume(Resume resume) {
        QResumeSection rs = QResumeSection.resumeSection;

        return queryFactory
                .select(Projections.constructor(
                        StartPracticeItem.class,
                        rs.question,   // -> title
                        rs.answer      // -> content
                ))
                .from(rs)
                .where(rs.resume.eq(resume))
                .fetch();
    }
}
