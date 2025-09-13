package com.example.Premind_BE.domain.interview.dao;

import com.example.Premind_BE.domain.interview.domain.InterviewModeType;
import com.example.Premind_BE.domain.interview.domain.QInterviewRecord;
import com.example.Premind_BE.domain.interview.domain.QTotalFeedback;
import com.example.Premind_BE.domain.interview.dto.response.PreviousRecordsResDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InterviewRecordRepositoryCustomImpl implements InterviewRecordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PreviousRecordsResDto> findByAllRecord(String interviewMode) {
        QInterviewRecord interviewRecord = QInterviewRecord.interviewRecord;
        QTotalFeedback totalFeedback = QTotalFeedback.totalFeedback;

        BooleanBuilder builder = new BooleanBuilder();
        if (interviewMode != null && !interviewMode.isBlank()) {
            try {
                InterviewModeType type = InterviewModeType.valueOf(interviewMode.toUpperCase());
                builder.and(interviewRecord.interviewModeType.eq(type));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("❌ 잘못된 면접 모드 값: " + interviewMode);
            }
        }

        return queryFactory
                .select(Projections.constructor(
                        PreviousRecordsResDto.class,
                        interviewRecord.id,
                        interviewRecord.createdDate,
                        interviewRecord.jobMinor.name,  // 직무명

                        // ✅ company: resume.company → 없으면 portfolio.company → 둘 다 없으면 null
                        Expressions.stringTemplate(
                                "CASE " +
                                        "WHEN {0} IS NOT NULL THEN {1} " +
                                        "WHEN {2} IS NOT NULL THEN {3} " +
                                        "ELSE NULL END",
                                interviewRecord.resume, interviewRecord.resume.company,
                                interviewRecord.portfolio, interviewRecord.portfolio.company
                        ),

                        // ✅ data: resume 존재 → '자기소개서', portfolio 존재 → '포트폴리오', 둘 다 없으면 null
                        Expressions.stringTemplate(
                                "CASE " +
                                        "WHEN {0} IS NOT NULL THEN '자기소개서' " +
                                        "WHEN {1} IS NOT NULL THEN '포트폴리오' " +
                                        "ELSE NULL END",
                                interviewRecord.resume,
                                interviewRecord.portfolio
                        ),

                        totalFeedback.totalScore.coalesce(0),
                        interviewRecord.interviewModeType.stringValue()
                ))
                .from(interviewRecord)
                .leftJoin(totalFeedback).on(totalFeedback.interviewRecord.eq(interviewRecord))
                .where(builder)
                .orderBy(interviewRecord.createdDate.desc())
                .fetch();
    }

}
