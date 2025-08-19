package com.example.Premind_BE.domain.resume.dao;

import com.example.Premind_BE.domain.resume.domain.ResumeSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeSectionRepository extends JpaRepository<ResumeSection, Long>, ResumeSectionRepositoryCustom {

}
