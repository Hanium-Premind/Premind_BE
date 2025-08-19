package com.example.Premind_BE.domain.resume.dao;

import com.example.Premind_BE.domain.interview.dto.ai.request.StartPracticeItem;
import com.example.Premind_BE.domain.resume.domain.Resume;

import java.util.List;

public interface ResumeSectionRepositoryCustom {
    List<StartPracticeItem> findByResume(Resume resume);
}
