package com.example.Premind_BE.domain.job.api;

import com.example.Premind_BE.domain.job.dto.response.MajorJobResDto;
import com.example.Premind_BE.domain.job.dto.response.MiddleJobResDto;
import com.example.Premind_BE.domain.job.dto.response.MinorJobResDto;
import com.example.Premind_BE.domain.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@Tag(name = "Job API", description = "직무 관련 API입니다.")
public class JobController {
    private final JobService jobService;

    @GetMapping("/list/major")
    @Operation(summary = "지원 직무 대분류 리스트 조회 API", description = "자기소개서 업로드 페이지에서 지원 직무 선택을 하기 위한 직무 대분류 리스트 조회 API입니다.")
    public List<MajorJobResDto> majorList() {
        return jobService.majorList();
    }

    @GetMapping("/list/middle")
    @Parameter(name = "parentId", description = "조회하고자하는 대분류 id값")
    @Operation(summary = "지원 직무 중분류 리스트 조회 API", description = "자기소개서 업로드 페이지에서 지원 직무 선택을 하기 위한 직무 중분류 리스트 조회 API입니다.")
    public List<MiddleJobResDto> middleList(@RequestParam Long parentId) {
        return jobService.middleList(parentId);
    }

    @GetMapping("/list/minor")
    @Parameter(name = "parentId", description = "조회하고자하는 중분류 id값")
    @Operation(summary = "지원 직무 소분류 리스트 조회 API", description = "자기소개서 업로드 페이지에서 지원 직무 선택을 하기 위한 직무 소분류 리스트 조회 API입니다.")
    public List<MinorJobResDto> minorList(@RequestParam Long parentId) {
        return jobService.minorList(parentId);
    }
}
