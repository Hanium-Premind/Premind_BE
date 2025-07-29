package com.example.Premind_BE.domain.user.service;

import com.example.Premind_BE.domain.password.dto.request.VerifyCodeReqDto;
import com.example.Premind_BE.domain.password.service.SmsService;
import com.example.Premind_BE.domain.user.dao.InterestJobRepository;
import com.example.Premind_BE.domain.user.dao.UserRepository;
import com.example.Premind_BE.domain.user.domain.InterestJob;
import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.domain.user.dto.request.RegisterReqDto;
import com.example.Premind_BE.domain.user.dto.request.UpdatePersonalInfoReqDto;
import com.example.Premind_BE.domain.user.dto.request.UserReceiveCodeReqDto;
import com.example.Premind_BE.domain.user.dto.response.PersonalInfoResDto;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.global.util.RedisUtil;
import com.example.Premind_BE.global.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final InterestJobRepository interestJobRepository;
    private final SmsService smsService;
    private final RedisUtil redisUtil;
    private final UserUtil userUtil;

    public User userRegister(RegisterReqDto registerReqDto) {
        String phoneNumber = registerReqDto.getPhoneNumber();

        //  전화번호 인증 여부 확인
        String isVerified = redisUtil.get("verify:" + phoneNumber);
        if (!"true".equals(isVerified)) {
            throw new CustomException(ErrorCode.PHONE_NOT_VERIFIED);
        }

        // 이미 존재하는 이메일 체크
        emailCheck(registerReqDto.getEmail());

        // 사용자 저장
        User savedUser = userRepository.save(
                User.builder()
                        .email(registerReqDto.getEmail())
                        .password(bCryptPasswordEncoder.encode(registerReqDto.getPassword()))
                        .name(registerReqDto.getName())
                        .birth(registerReqDto.getBirthAsLocalDate())
                        .gender(registerReqDto.getGender())
                        .phoneNumber(phoneNumber)
                        .build()
        );

        // 관심 직무 저장
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

    public void receiveCode(UserReceiveCodeReqDto sendCodeRequestDto) {
        smsService.certificateSMS(sendCodeRequestDto.getPhoneNumber());
    }

    public void verifyCode(VerifyCodeReqDto verifyCodeReqDto) {
        smsService.verifyCode(verifyCodeReqDto.getPhoneNumber(), verifyCodeReqDto.getCode());
    }

    public PersonalInfoResDto personalInfo() {
        User currentMember = userUtil.getCurrentUser();

        List<String> jobNames = interestJobRepository.findByUser(currentMember)
                .stream()
                .map(InterestJob::getJob)
                .collect(Collectors.toList());

        return PersonalInfoResDto.builder()
                .email(currentMember.getEmail())
                .name(currentMember.getName())
                .birth(currentMember.getBirth().toString())
                .gender(currentMember.getGender().toString())
                .phoneNumber(currentMember.getPhoneNumber())
                .interestJobs(jobNames)
                .build();
    }


    @Transactional
    public void updatePersonalInfo(UpdatePersonalInfoReqDto dto) {
        User user = userUtil.getCurrentUser();
        user.updateInfo(dto);
    }

}
