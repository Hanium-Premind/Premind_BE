package com.example.Premind_BE.domain.auth.api;

import com.example.Premind_BE.domain.auth.dto.request.LoginReqDto;
import com.example.Premind_BE.domain.auth.dto.request.ReissueReqDto;
import com.example.Premind_BE.domain.auth.dto.response.LoginResDto;
import com.example.Premind_BE.domain.auth.dto.response.ReissueResDto;
import com.example.Premind_BE.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "리프레시 토큰으로 액세스 토큰 재발급")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
            @ApiResponse(responseCode = "401", description = "리프레시 토큰이 만료되었거나 유효하지 않음")
    })
    @PostMapping("/reissue")
    public ReissueResDto refreshToken(@RequestBody ReissueReqDto requestDto) {
        return authService.reissue(requestDto);
    }
}
