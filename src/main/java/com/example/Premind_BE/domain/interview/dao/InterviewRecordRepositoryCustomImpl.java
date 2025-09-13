package com.example.Premind_BE.domain.interview.dao;

import com.example.Premind_BE.domain.interview.domain.InterviewModeType;
import com.example.Premind_BE.domain.interview.domain.QInterviewRecord;
import com.example.Premind_BE.domain.interview.domain.QTotalFeedback;
import com.example.Premind_BE.domain.interview.dto.response.PreviousRecordsResDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InterviewRecordRepositoryCustomImpl implements InterviewRecordRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PreviousRecordsResDto> findByAllRecord(InterviewModeType interviewMode) {
        QInterviewRecord interviewRecord = QInterviewRecord.interviewRecord;
        QTotalFeedback totalFeedback = QTotalFeedback.totalFeedback;

        return queryFactory
                .select(Projections.constructor(
                        PreviousRecordsResDto.class,
                        interviewRecord.id,
                        interviewRecord.createdDate,
                        interviewRecord.jobMinor.name,
                        // Resume, Portfolio 중 어떤게 있는지 체크
                        new CaseBuilder()
                                .when(interviewRecord.resume.isNotNull())
                                .then(interviewRecord.resume.company)
                                .otherwise(interviewRecord.portfolio.company),
                        new CaseBuilder()
                                .when(interviewRecord.resume.isNotNull())
                                .then("자기소개서")
                                .otherwise("포트폴리오"),
                        totalFeedback.totalScore,
                        interviewRecord.interviewModeType.stringValue()
                ))
                .from(interviewRecord)
                .leftJoin(totalFeedback).on(totalFeedback.interviewRecord.eq(interviewRecord))
                .where(
                        interviewMode != null
                                ? interviewRecord.interviewModeType.eq(interviewMode)
                                : null   // null 이면 where 조건 없이 전체 조회
                )
                .orderBy(interviewRecord.createdDate.desc())
                .fetch();
    }

}
