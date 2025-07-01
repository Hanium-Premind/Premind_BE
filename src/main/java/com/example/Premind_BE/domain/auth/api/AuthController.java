package com.example.Premind_BE.domain.auth.api;

import com.example.Premind_BE.domain.auth.dto.request.LoginReqDto;
import com.example.Premind_BE.domain.auth.dto.response.LoginResDto;
import com.example.Premind_BE.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "인증 관련 API입니다.")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResDto login(@RequestBody LoginReqDto request) {
        return authService.login(request);
    }
}
