package com.example.Premind_BE.domain.user.service;

import com.example.Premind_BE.domain.password.service.SmsService;
import com.example.Premind_BE.domain.user.dao.InterestJobRepository;
import com.example.Premind_BE.domain.user.dao.UserRepository;
import com.example.Premind_BE.domain.user.domain.InterestJob;
import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.domain.user.dto.request.RegisterReqDto;
import com.example.Premind_BE.domain.user.dto.request.RegisterVerifyCodeReqDto;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
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
    private final UserUtil userUtil;
    private final RedisUtil redisUtil;

    public User userRegister(RegisterReqDto registerReqDto) {
        String phoneNumber = registerReqDto.getPhoneNumber();

        //  전화번호 인증 여부 확인
        String isVerified = redisUtil.get("verify:" + phoneNumber);
        if (!"true".equals(isVerified)) {
            throw new CustomException(ErrorCode.PHONE_NOT_VERIFIED);
        }

        // 이미 존재하는 이메일 체크
        usernameCheck(registerReqDto.getUsername());

        return userRepository.save(
                User.builder()
                        .username(registerReqDto.getUsername())
                        .password(bCryptPasswordEncoder.encode(registerReqDto.getPassword()))
                        .name(registerReqDto.getName())
                        .email(registerReqDto.getEmail())
                        .birth(registerReqDto.getBirthAsLocalDate())
                        .gender(registerReqDto.getGender())
                        .phoneNumber(phoneNumber)
                        .build()
        );
    }

    public boolean usernameCheck(String username) {
        // 이미 존재하는 이메일로 회원가입시 오류 발생
        if(userRepository.findByUsername(username).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_ID);
        }
        return true; // 사용 가능
    }

    @Transactional(Transactional.TxType.NEVER)
    public void receiveCode(UserReceiveCodeReqDto sendCodeRequestDto) {
        smsService.certificateSMS(sendCodeRequestDto.getPhoneNumber());
    }

    // 인증번호 검증 (트랜잭션 필요 X)
    @Transactional(Transactional.TxType.NEVER)
    public void verifyCode(RegisterVerifyCodeReqDto req) {
        smsService.verifyCode(req.getPhoneNumber(), req.getCode());
    }

    public PersonalInfoResDto personalInfo() {
        User currentMember = userUtil.getCurrentUser();

        List<String> jobNames = interestJobRepository.findByUser(currentMember)
                .stream()
                .map(InterestJob::getJob)
                .collect(Collectors.toList());

        return PersonalInfoResDto.builder()
                .username(currentMember.getUsername())
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
