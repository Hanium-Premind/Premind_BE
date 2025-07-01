package com.example.Premind_BE.domain.user.service;

import com.example.Premind_BE.domain.user.dao.UserRepository;
import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.domain.user.dto.request.RegisterReqDto;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public User userRegister(RegisterReqDto registerReqDto) {
        // 이미 존재하는 이메일로 회원가입시 오류 발생
        if(userRepository.findByEmail(registerReqDto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 회원가입 과정
        return userRepository.save(
                User.builder()
                        .email(registerReqDto.getEmail())
                        .password(bCryptPasswordEncoder.encode(registerReqDto.getPassword())) // 비밀번호 암호화
                        .name(registerReqDto.getName())
                        .birth(registerReqDto.getBirthAsLocalDate())
                        .gender(registerReqDto.getGender())
                        .phoneNumber(registerReqDto.getPhoneNumber())
                        .build()
        );
    }




}
