package com.example.Premind_BE.domain.job.dao;

import com.example.Premind_BE.domain.job.domain.JobCategory;
import com.example.Premind_BE.domain.job.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
    List<JobCategory> findByLevel(Level level);
    JobCategory findByLevelAndId(Level level, Long id);
    List<JobCategory> findByLevelAndParentId(Level level, Long parentId);
}
