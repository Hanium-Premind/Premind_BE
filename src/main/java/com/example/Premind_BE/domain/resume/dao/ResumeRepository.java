package com.example.Premind_BE.domain.resume.dao;

import com.example.Premind_BE.domain.resume.domain.Resume;
import com.example.Premind_BE.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long>, ResumeRepositoryCustom {
}
