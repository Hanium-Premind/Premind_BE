package com.example.Premind_BE.domain.user.service;

import com.example.Premind_BE.domain.user.dao.InterestJobRepository;
import com.example.Premind_BE.domain.user.dao.UserRepository;
import com.example.Premind_BE.domain.user.domain.InterestJob;
import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.domain.user.dto.request.RegisterReqDto;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final InterestJobRepository interestJobRepository;

    public User userRegister(RegisterReqDto registerReqDto) {
        // 이미 존재하는 이메일로 회원가입시 오류 발생
        emailCheck(registerReqDto.getEmail());

        // 사용자 저장
        User savedUser = userRepository.save(
                User.builder()
                        .email(registerReqDto.getEmail())
                        .password(bCryptPasswordEncoder.encode(registerReqDto.getPassword()))
                        .name(registerReqDto.getName())
                        .birth(registerReqDto.getBirthAsLocalDate())
                        .gender(registerReqDto.getGender())
                        .phoneNumber(registerReqDto.getPhoneNumber())
                        .build()
        );

        interestJobRepository.saveAll(registerReqDto.getInterestJobs().stream()
                .map(job -> InterestJob.builder()
                        .user(savedUser)
                        .job(job)
                        .build())
                .toList()
        );

        return savedUser;
    }



    public boolean emailCheck(String email) {
        // 이미 존재하는 이메일로 회원가입시 오류 발생
        if(userRepository.findByEmail(email).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        return true; // 사용 가능
    }

    private User getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // subject → email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

}
