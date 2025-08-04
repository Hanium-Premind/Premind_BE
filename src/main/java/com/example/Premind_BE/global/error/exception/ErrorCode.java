package com.example.Premind_BE.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TEST_ERROR_CODE(HttpStatus.BAD_REQUEST, "오류가 발생하였습니다."),
    DUPLICATE_ID(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,  "존재하지 않는 사용자입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    EMAIL_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
    INVALID_USER_INFORMATION(HttpStatus.BAD_REQUEST, "잘못된 사용자 정보입니다."),
    VERIFICATION_CODE_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "인증번호 발송에 실패하였습니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "인증번호가 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    JOB_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 직무 유형입니다."),
    NOT_EXIST_VERIFICATION_RECORD(HttpStatus.NOT_FOUND, "인증내역이 존재하지 않습니다."),
    PHONE_NOT_VERIFIED(HttpStatus.BAD_REQUEST, "인증내역이 존재하지 않습니다."),
    PASSWORD_REAUTH_REQUIRED(HttpStatus.BAD_REQUEST, "기존 비밀번호 인증내역이 존재하지 않습니다." ),
    RESUME_ACCESS_DENIED(HttpStatus.FORBIDDEN, "자소서에 대한 접근 권한이 없습니다."),
    RESUME_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 자소서 입니다."),
    PORTFOLIO_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 포트폴리오 입니다."),
    PORTFOLIO_ACCESS_DENIED(HttpStatus.FORBIDDEN, "포트폴리오에 대한 접근 권한이 없습니다.")
    ;


    private final HttpStatus status;
    private final String message;
}
