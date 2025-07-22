package com.example.Premind_BE.domain.resume.dao;

import com.example.Premind_BE.domain.resume.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
