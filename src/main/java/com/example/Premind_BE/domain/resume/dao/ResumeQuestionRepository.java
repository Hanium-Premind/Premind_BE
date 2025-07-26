package com.example.Premind_BE.domain.resume.dao;

import com.example.Premind_BE.domain.resume.domain.ResumeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeQuestionRepository extends JpaRepository<ResumeQuestion, Long> {
}
