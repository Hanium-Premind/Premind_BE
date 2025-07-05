package com.example.Premind_BE.domain.user.api;

import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.domain.user.dto.request.RegisterReqDto;
import com.example.Premind_BE.domain.user.dto.response.EmailCheckResDto;
import com.example.Premind_BE.domain.user.service.UserService;
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

    // 비밀번호 고치기

    // 만약 전화번호 인증이 되면 인증 내역 redis에 저장 후 회원가입시 인증 내역이 있는 경우에만 회원가입 가능함
}
