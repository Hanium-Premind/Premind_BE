package com.example.Premind_BE.domain.password.service;

import com.example.Premind_BE.domain.password.dao.VerificationRecordRepository;
import com.example.Premind_BE.domain.password.domain.VerificationRecord;
import com.example.Premind_BE.domain.password.dto.request.ReceiveCodeReqDto;
import com.example.Premind_BE.domain.password.dto.request.VerifyCodeReqDto;
import com.example.Premind_BE.domain.password.dto.response.EmailCheckResDto;
import com.example.Premind_BE.domain.user.dao.UserRepository;
import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordService {
    private final UserRepository userRepository;
    private final SmsService smsService;
    private final VerificationRecordRepository verificationRecordRepository;

    public EmailCheckResDto emailCheck(String email) {
        if(userRepository.existsByEmail(email)) return new EmailCheckResDto("존재하는 이메일 정보입니다.");
        else throw new CustomException(ErrorCode.EMAIL_NOT_EXIST);
    }

    public void receiveCode(ReceiveCodeReqDto receiveCodeReqDto) {
        // 요청한 정보로 사용자 정보를 찾는다.
        User user = userRepository.findByNameAndPhoneNumber(receiveCodeReqDto.getName(), receiveCodeReqDto.getPhoneNumber()).orElseThrow(() -> {
            throw new CustomException(ErrorCode.INVALID_USER_INFORMATION);
        });
        smsService.certificateSMS(receiveCodeReqDto.getPhoneNumber());
    }

    public void verifyCode(VerifyCodeReqDto verifyCodeReqDto) {
        smsService.verifyCode(verifyCodeReqDto.getPhoneNumber(), verifyCodeReqDto.getCode());
        verificationRecordRepository.save(
                new VerificationRecord(verifyCodeReqDto.getPhoneNumber(), verifyCodeReqDto.getCode())
        );
    }
}
