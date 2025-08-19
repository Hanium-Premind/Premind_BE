package com.example.Premind_BE.domain.interview.dao;

import com.example.Premind_BE.domain.interview.domain.InterviewRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRecordRepository extends JpaRepository<InterviewRecord, Long> {
}
