package com.example.Premind_BE.domain.interview.dao;

import com.example.Premind_BE.domain.interview.domain.InterviewModeType;
import com.example.Premind_BE.domain.interview.dto.response.PreviousRecordsResDto;

import java.util.List;

public interface InterviewRecordRepositoryCustom {
    List<PreviousRecordsResDto> findByAllRecord(InterviewModeType interviewMode);
}
