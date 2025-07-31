package com.example.Premind_BE.domain.job.service;

import com.example.Premind_BE.domain.job.dao.JobCategoryRepository;
import com.example.Premind_BE.domain.job.domain.JobCategory;
import com.example.Premind_BE.domain.job.domain.Level;
import com.example.Premind_BE.domain.job.dto.response.MajorJobResDto;
import com.example.Premind_BE.domain.job.dto.response.MiddleJobResDto;
import com.example.Premind_BE.domain.job.dto.response.MinorJobResDto;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class JobService {
    private final JobCategoryRepository jobCategoryRepository;

    public List<MajorJobResDto> majorList() {
        return jobCategoryRepository.findByLevel(Level.MAJOR)
                .stream()
                .map(job -> new MajorJobResDto(
                        job.getId(),  // 또는 Integer 그대로
                        job.getName(),
                        job.getLevel()
                ))
                .collect(Collectors.toList());
    }

    public List<MiddleJobResDto> middleList(Long parentId) {
        return jobCategoryRepository.findByLevelAndParentId(Level.MIDDLE, parentId)
                .stream()
                .map(job -> new MiddleJobResDto(
                        job.getId(),
                        job.getParentId(),
                        job.getName(),
                        Level.MIDDLE
                ))
                .collect(Collectors.toList());
    }

    public List<MinorJobResDto> minorList(Long parentId) {
        return jobCategoryRepository.findByLevelAndParentId(Level.MINOR, parentId)
                .stream()
                .map(job -> new MinorJobResDto(
                        job.getId(),
                        job.getParentId(),
                        job.getName(),
                        Level.MINOR
                ))
                .collect(Collectors.toList());
    }

    public List<JobCategory> findJobCategory(Long jobMajorId, Long jobMiddleId, Long jobMinorId) {
        List<Long> ids = List.of(jobMajorId, jobMiddleId, jobMinorId);

        List<JobCategory> categories = jobCategoryRepository.findAllByIdIn(ids);
        if (categories.size() != 3) {
            throw new CustomException(ErrorCode.JOB_CATEGORY_NOT_FOUND);
        }

        JobCategory major = findByLevelOrThrow(categories, Level.MAJOR);
        JobCategory middle = findByLevelOrThrow(categories, Level.MIDDLE);
        JobCategory minor = findByLevelOrThrow(categories, Level.MINOR);

        return List.of(major, middle, minor);
    }

    private JobCategory findByLevelOrThrow(List<JobCategory> list, Level level) {
        return list.stream()
                .filter(c -> c.getLevel() == level)
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.JOB_CATEGORY_NOT_FOUND));
    }
}
