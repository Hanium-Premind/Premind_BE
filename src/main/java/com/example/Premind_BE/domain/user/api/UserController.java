package com.example.Premind_BE.domain.user.api;

import com.example.Premind_BE.domain.password.dto.request.ReceiveCodeReqDto;
import com.example.Premind_BE.domain.password.dto.request.VerifyCodeReqDto;
import com.example.Premind_BE.domain.password.dto.response.ReceiveCodeResDto;
import com.example.Premind_BE.domain.password.dto.response.VerifyCodeResDto;
import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.domain.user.dto.request.RegisterReqDto;
import com.example.Premind_BE.domain.user.dto.request.UserReceiveCodeReqDto;
import com.example.Premind_BE.domain.user.dto.response.EmailCheckResDto;
import com.example.Premind_BE.domain.user.service.UserService;
import com.example.Premind_BE.global.common.response.MessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User API", description = "사용자 관련 API입니다.")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입", description = "회원가입시 중복확인을 거친 이메일로만 회원가입 가능")
    @PostMapping("/register")
    public User userRegister(@RequestBody RegisterReqDto registerReqDto) {
        return userService.userRegister(registerReqDto);
    }

    @Operation(summary = "이메일 중복 확인", description = "회원가입 단계에서 이메일을 중복확인하여 사용가능한 이메일인지 확인할 수 있다.")
    @Parameter(name = "nickname", description = "중복 확인 하고자 하는 이메일")
    @GetMapping("/email/check")
    public EmailCheckResDto emailCheck(@RequestParam String email) {
        boolean isAvailable = userService.emailCheck(email);

        return new EmailCheckResDto("사용 가능한 이메일입니다.", isAvailable);
    }

    @Operation(summary = "회원가입에서 인증번호 받기", description = "회원가입에서 인증번호 받기")
    @PostMapping("/receive/code")
    public MessageDto receiveCode(@RequestBody UserReceiveCodeReqDto sendCodeRequestDto) {
        userService.receiveCode(sendCodeRequestDto);
        return new MessageDto("인증번호가 발송되었습니다.");
    }

    @Operation(summary = "회원가입에서 인증번호 검증", description = "전화번호와 인증번호가 일치하는지 확인합니다.")
    @PostMapping("/verify/code")
    public MessageDto verifyCode(@RequestBody VerifyCodeReqDto verifyCodeReqDto) {
        userService.verifyCode(verifyCodeReqDto);
        return new MessageDto("인증이 완료되었습니다.");
    }
}
