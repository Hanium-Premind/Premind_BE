package com.example.Premind_BE.domain.auth.service;

import com.example.Premind_BE.domain.auth.dao.RefreshTokenRepository;
import com.example.Premind_BE.domain.auth.domain.RefreshToken;
import com.example.Premind_BE.domain.auth.dto.request.LoginReqDto;
import com.example.Premind_BE.domain.auth.dto.response.LoginResDto;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
}
