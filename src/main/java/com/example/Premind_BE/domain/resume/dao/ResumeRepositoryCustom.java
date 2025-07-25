package com.example.Premind_BE.domain.resume.dao;

import com.example.Premind_BE.domain.resume.dto.response.ResumeInquiryResDto;
import com.example.Premind_BE.domain.resume.dto.response.ResumeListResDto;
import com.example.Premind_BE.domain.user.domain.User;

import java.util.List;

public interface ResumeRepositoryCustom {
    List<ResumeListResDto> findAllResumeListWithJobMinor(User currentUser);
    ResumeInquiryResDto findResumeInquiry(Long resumeId);
}
