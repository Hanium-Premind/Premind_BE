package com.example.Premind_BE.global.util;


import com.example.Premind_BE.domain.auth.dto.CustomUserDetails;
import com.example.Premind_BE.domain.interview.domain.InterviewRecord;
import com.example.Premind_BE.domain.portfolio.domain.Portfolio;
import com.example.Premind_BE.domain.resume.domain.Resume;
import com.example.Premind_BE.domain.user.dao.UserRepository;
import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // subject → email
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void verifyResumeUser(Resume resume) {
        if(!resume.getUser().equals(getCurrentUser())) {
            throw new CustomException(ErrorCode.RESUME_ACCESS_DENIED);
        }
    }

    public void verifyPortfolioUser(Portfolio portfolio) {
        if(!portfolio.getUser().equals(getCurrentUser())) {
            throw new CustomException(ErrorCode.PORTFOLIO_ACCESS_DENIED);
        }
    }

    public void verifyInterviewReccordUser(InterviewRecord record) {
        if(!record.getUser().equals(getCurrentUser())) {
            throw new CustomException(ErrorCode.INTERVIEW_RECORD_ACCESS_DENIED);
        }
    }
}
