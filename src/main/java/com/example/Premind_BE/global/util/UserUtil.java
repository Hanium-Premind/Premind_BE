package com.example.Premind_BE.global.util;


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
        String email = authentication.getName(); // subject → email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
