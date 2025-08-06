package com.example.Premind_BE.domain.resume.dao;

import com.example.Premind_BE.domain.resume.domain.ResumeQuestion;
import org.springframework.data.repository.CrudRepository;

public interface ResumeQuestionRedisRepository extends CrudRepository<ResumeQuestion, Long> {
}
