package com.example.Premind_BE.domain.user.api;

import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.domain.user.dto.request.RegisterReqDto;
import com.example.Premind_BE.domain.user.dto.request.RegisterVerifyCodeReqDto;
import com.example.Premind_BE.domain.user.dto.request.UpdatePersonalInfoReqDto;
import com.example.Premind_BE.domain.user.dto.request.UserReceiveCodeReqDto;
import com.example.Premind_BE.domain.user.dto.response.UsernameCheckResDto;
import com.example.Premind_BE.domain.user.dto.response.PersonalInfoResDto;
import com.example.Premind_BE.domain.user.service.UserService;
import com.example.Premind_BE.global.common.response.MessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "사용자 관련 API입니다.")
public class UserController {
    private final UserService userService;

    @Operation(summary = "아이디 중복 확인", description = "회원가입 단계에서 이메일을 중복확인하여 사용가능한 이메일인지 확인할 수 있다.")
    @Parameter(name = "username", description = "중복 확인 하고자 하는 이메일")
    @GetMapping("/username/check")
    public UsernameCheckResDto usernameCheck(@RequestParam String username) {
        boolean isAvailable = userService.usernameCheck(username);
        return new UsernameCheckResDto("사용 가능한 이메일입니다.", isAvailable);
    }

    @Operation(summary = "회원가입 페이지에서 인증번호 받기")
    @PostMapping("/receive/code")
    public MessageDto receiveCode(@Valid @RequestBody UserReceiveCodeReqDto sendCodeRequestDto) {
        userService.receiveCode(sendCodeRequestDto);
        return new MessageDto("인증번호가 발송되었습니다.");
    }

    @Operation(summary = "회원가입 페이지에서 인증번호 검증하기")
    @PostMapping("/verify/code")
    public MessageDto verifyCode(@Valid @RequestBody RegisterVerifyCodeReqDto registerVerifyCodeReqDto) {
         userService.verifyCode(registerVerifyCodeReqDto);
         return new MessageDto("인증이 완료되었습니다.");
    }

    @Operation(summary = "회원가입을 위한 요청", description = "전화번호 인증이 완료된 사용자만이 회원가입이 가능하다.")
    @PostMapping("/register")
    public User userRegister(@RequestBody RegisterReqDto registerReqDto) {
        return userService.userRegister(registerReqDto);
    }

    @Operation(summary = "개인 정보 조회", description = "사용자 정보를 조회합니다.")
    @GetMapping("/personal-info")
    public PersonalInfoResDto personalInfo() {
        return userService.personalInfo();
    }

    @Operation(summary = "개인 정보 수정", description = "개인 정보를 수정하는 API입니다.")
    @PutMapping("/update/personal-info")
    public MessageDto updatePersonalInfo(@Valid @RequestBody UpdatePersonalInfoReqDto updatePersonalInfoReqDto) {
        userService.updatePersonalInfo(updatePersonalInfoReqDto);
        return new MessageDto("개인 정보가 수정되었습니다.");
    }
}
