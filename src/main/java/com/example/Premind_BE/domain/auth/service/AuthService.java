package com.example.Premind_BE.domain.auth.service;

import com.example.Premind_BE.domain.auth.dao.RefreshTokenRepository;
import com.example.Premind_BE.domain.auth.domain.RefreshToken;
import com.example.Premind_BE.domain.auth.dto.request.LoginReqDto;
import com.example.Premind_BE.domain.auth.dto.request.ReissueReqDto;
import com.example.Premind_BE.domain.auth.dto.response.LoginResDto;
import com.example.Premind_BE.domain.auth.dto.response.ReissueResDto;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginResDto login(LoginReqDto request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );

            String accessToken = jwtUtil.createAccessToken(request.getEmail());
            String refreshToken = jwtUtil.createRefreshToken(request.getEmail());

            refreshTokenRepository.save(new RefreshToken(request.getEmail(), refreshToken));

            return new LoginResDto(accessToken, refreshToken);

        }catch (AuthenticationException e) {
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }
    }

    public ReissueResDto reissue(ReissueReqDto requestDto) {
        String refreshToken = requestDto.getRefreshToken();

        // 리프레시 토큰이 유효한지 확인
        if (jwtUtil.isExpired(refreshToken)) {
            throw new CustomException(ErrorCode.TOKEN_EXPIRED); // 토큰 만료 오류
        }

        // 리프레시 토큰이 DB에 존재하는지 확인
        RefreshToken storedRefreshToken = refreshTokenRepository.findByToken(jwtUtil.getEmail(refreshToken))
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_REFRESH_TOKEN)); // 유효하지 않은 리프레시 토큰 오류

        // 이메일을 이용하여 새로운 액세스 토큰 발급
        String email = jwtUtil.getEmail(refreshToken);
        String newAccessToken = jwtUtil.createAccessToken(email);

        // 새로운 리프레시 토큰 발급
        String newRefreshToken = jwtUtil.createRefreshToken(email);

        // 기존 리프레시 토큰을 갱신하여 저장
        storedRefreshToken.updateToken(newRefreshToken);
        refreshTokenRepository.save(storedRefreshToken);

        return new ReissueResDto(newAccessToken, newRefreshToken);
    }

}
