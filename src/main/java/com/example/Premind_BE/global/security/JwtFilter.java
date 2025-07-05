package com.example.Premind_BE.global.security;

import com.example.Premind_BE.domain.user.domain.User;
import com.example.Premind_BE.domain.auth.dto.CustomUserDetails;
import com.example.Premind_BE.global.error.exception.CustomException;
import com.example.Premind_BE.global.error.exception.ErrorCode;
import com.example.Premind_BE.global.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("Authorization header is missing or does not start with Bearer.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        try {
            // 토큰 만료 확인 후 CustomException으로 처리
            if (jwtUtil.isExpired(token)) {
                log.error("토큰 만료: {}", token);
                throw new CustomException(ErrorCode.TOKEN_EXPIRED); // CustomException을 던짐
            }

            String email = jwtUtil.getEmail(token);

            // 임시 User 생성 (DB 조회 대신)
            User userEntity = User.builder()
                    .email(email)
                    .password("temppassword") // 주의: 비밀번호는 사용되지 않음
                    .build();

            CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails, null, customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);
            log.info("JWT 인증 성공: 사용자 이메일 - {}", email);

        } catch (ExpiredJwtException e) {
            log.error("JWT 토큰 만료 오류: {}", e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "EXPIRED_TOKEN", "토큰이 만료되었습니다.");
            return;

        } catch (SignatureException e) {
            log.error("JWT 서명 오류: {}", e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "INVALID_REFRESH_TOKEN", "유효하지 않은 리프레시 토큰입니다.");
            return;

        } catch (Exception e) {
            log.error("토큰 검증 오류: {}", e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "INVALID_TOKEN", "유효하지 않은 토큰입니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void sendErrorResponse(HttpServletResponse response, int status,
                                   String errorCode, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String body = "{\n" +
                "  \"success\": false,\n" +
                "  \"status\": " + status + ",\n" +
                "  \"timestamp\": \"" + java.time.LocalDateTime.now() + "\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"" + message + "\",\n" +
                "    \"errorClassName\": \"" + errorCode + "\"\n" +
                "  }\n" +
                "}";

        response.getWriter().write(body);
    }
}
