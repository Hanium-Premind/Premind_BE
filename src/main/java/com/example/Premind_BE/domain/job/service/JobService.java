package com.example.Premind_BE.domain.job.service;

import com.example.Premind_BE.domain.job.dao.JobCategoryRepository;
import com.example.Premind_BE.domain.job.domain.Level;
import com.example.Premind_BE.domain.job.dto.response.MajorJobResDto;
import com.example.Premind_BE.domain.job.dto.response.MiddleJobResDto;
import com.example.Premind_BE.domain.job.dto.response.MinorJobResDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class JobService {
    private final JobCategoryRepository jobRepository;

    public List<MajorJobResDto> majorList() {
        return jobRepository.findByLevel(Level.MAJOR)
                .stream()
                .map(job -> new MajorJobResDto(
                        job.getId(),  // 또는 Integer 그대로
                        job.getName(),
                        job.getLevel()
                ))
                .collect(Collectors.toList());
    }

    public List<MiddleJobResDto> middleList(Long parentId) {
        return jobRepository.findByLevelAndParentId(Level.MIDDLE, parentId)
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
        return jobRepository.findByLevelAndParentId(Level.MINOR, parentId)
                .stream()
                .map(job -> new MinorJobResDto(
                        job.getId(),
                        job.getParentId(),
                        job.getName(),
                        Level.MINOR
                ))
                .collect(Collectors.toList());
    }
}
