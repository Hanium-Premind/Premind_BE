package com.example.Premind_BE.domain.password.api;

import com.example.Premind_BE.domain.password.dto.request.VerifyCodeReqDto;
import com.example.Premind_BE.domain.password.dto.response.ReceiveCodeResDto;
import com.example.Premind_BE.domain.password.dto.request.ReceiveCodeReqDto;
import com.example.Premind_BE.domain.password.dto.response.EmailCheckResDto;
import com.example.Premind_BE.domain.password.dto.response.VerifyCodeResDto;
import com.example.Premind_BE.domain.password.service.PasswordService;
import com.example.Premind_BE.global.common.response.GlobalResponse;
import com.example.Premind_BE.global.common.response.MessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
@RequiredArgsConstructor
@Tag(name = "Password API", description = "비밀번호 관련 API입니다.")
public class PasswordController {
    private final PasswordService passwordService;

    @Operation(summary = "비밀번호 찾기에서 이메일 확인하기", description = "비밀번호 찾기에서 이미 가입한 사용자만이 비밀번호 찾기를 할 수 있다.")
    @Parameter(name = "email", description = "찾고자하는 계정의 이메일")
    @GetMapping("/email/check")
    public EmailCheckResDto emailCheck(@RequestParam String email) {
        return passwordService.emailCheck(email);
    }

    @Operation(summary = "비밀번호 찾기에서 인증번호 받기", description = "비밀번호 찾기에서 인증번호 받기")
    @PostMapping("/receive/code")
    public ReceiveCodeResDto receiveCode(@RequestBody ReceiveCodeReqDto sendCodeRequestDto) {
        passwordService.receiveCode(sendCodeRequestDto);
        return new ReceiveCodeResDto("인증번호가 발송되었습니다.");
    }

    @Operation(summary = "비밀번호 찾기 인증번호 검증", description = "전화번호와 인증번호가 일치하는지 확인합니다.")
    @PostMapping("/verify/code")
    public VerifyCodeResDto verifyCode(@RequestBody VerifyCodeReqDto verifyCodeReqDto) {
        passwordService.verifyCode(verifyCodeReqDto);
        return new VerifyCodeResDto("인증이 완료되었습니다.");
    }
}

