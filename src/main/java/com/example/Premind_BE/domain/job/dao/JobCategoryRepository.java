package com.example.Premind_BE.domain.job.dao;

import com.example.Premind_BE.domain.job.domain.JobCategory;
import com.example.Premind_BE.domain.job.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
    List<JobCategory> findByLevel(Level level);
    Optional<JobCategory> findByIdAndLevel(Long id, Level level);
    List<JobCategory> findByLevelAndParentId(Level level, Long parentId);
}
