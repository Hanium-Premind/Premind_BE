package com.example.Premind_BE.domain.resume.dao;

import com.example.Premind_BE.domain.job.domain.QJobCategory;
import com.example.Premind_BE.domain.resume.domain.QResume;
import com.example.Premind_BE.domain.resume.dto.response.ResumeListResDto;
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
}
