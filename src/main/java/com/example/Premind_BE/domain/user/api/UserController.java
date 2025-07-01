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

import java.lang.reflect.Member;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Member API", description = "사용자 관련 API입니다.")
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
}
