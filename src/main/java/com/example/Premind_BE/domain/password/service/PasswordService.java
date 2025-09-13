package com.example.Premind_BE.domain.password.service;

import com.example.Premind_BE.domain.password.dto.request.ChangePasswordReqDto;
import com.example.Premind_BE.domain.password.dto.request.ReceiveCodeReqDto;
import com.example.Premind_BE.domain.password.dto.request.UpdatePasswordReqDto;
import com.example.Premind_BE.domain.password.dto.request.VerifyCodeReqDto;
import com.example.Premind_BE.domain.user.dao.UserRepository;
import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.global.common.response.MessageDto;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.global.util.RedisUtil;
import com.example.Premind_BE.global.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordService {
    private final UserRepository userRepository;
    private final SmsService smsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisUtil redisUtil;
    private final UserUtil userUtil;

    public MessageDto usernameCheck(String username, String email) {
        if(userRepository.existsByUsernameAndEmail(username, email)) return new MessageDto("존재하는 아이디 정보입니다.");
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
        String phoneNumber = verifyCodeReqDto.getPhoneNumber();
        String code = verifyCodeReqDto.getCode();

        // 인증번호 검증 (예외 발생 시 중단)
        smsService.verifyCode(phoneNumber, code);

        // 인증 성공 시 Redis에 인증 완료 내역 저장
        redisUtil.set("verify:" + phoneNumber, "true", Duration.ofMinutes(10));
    }


    public void updatePassword(UpdatePasswordReqDto updatePasswordReqDto) {
        // 이메일로 사용자 정보 찾기
        User user = userRepository.findByUsername(updatePasswordReqDto.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String isVerified = redisUtil.get("verify:" + user.getPhoneNumber());
        if (!"true".equals(isVerified)) {
            throw new CustomException(ErrorCode.PHONE_NOT_VERIFIED);
        }

        // 새로운 비밀번호로 변경
        user.updatePassword(bCryptPasswordEncoder.encode(updatePasswordReqDto.getNewPassword()));
    }


    public void verifyPassword(String password) {
        User user = userUtil.getCurrentUser(); // 현재 로그인한 사용자
        Long userId = user.getId();     // Redis 키 식별용 ID

        // 비밀번호 불일치 시 예외 발생
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD); // 비밀번호 불일치
        }

        redisUtil.set("password_verified:" + userId, "true", Duration.ofMinutes(15));
    }

    public void changePassword(ChangePasswordReqDto changePasswordReqDto) {
        User user = userUtil.getCurrentUser();
        String verified = redisUtil.get("password_verified:" + user.getId());
        if (!"true".equals(verified)) {
            throw new CustomException(ErrorCode.PASSWORD_REAUTH_REQUIRED);
        }

        // 새로운 비밀번호로 변경
        user.updatePassword(bCryptPasswordEncoder.encode(changePasswordReqDto.getNewPassword()));
    }
}
