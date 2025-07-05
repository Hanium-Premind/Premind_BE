package com.example.Premind_BE.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    TEST_ERROR_CODE(HttpStatus.BAD_REQUEST, "오류가 발생하였습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,  "존재하지 않는 사용자입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    EMAIL_NOT_EXIST(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."),
    INVALID_USER_INFORMATION(HttpStatus.BAD_REQUEST, "잘못된 사용자 정보입니다."),
    VERIFICATION_CODE_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "인증번호 발송에 실패하였습니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.UNAUTHORIZED, "인증번호가 일치하지 않습니다.")
    ;



    private final HttpStatus status;
    private final String message;
}
