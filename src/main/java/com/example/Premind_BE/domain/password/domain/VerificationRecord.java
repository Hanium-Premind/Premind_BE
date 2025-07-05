package com.example.Premind_BE.domain.password.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "phoneVerificationRecord", timeToLive = 60 * 5) // 5분 TTL (초)
public class VerificationRecord implements Serializable {

    @Id
    private String phoneNumber;

    private String code;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // 전화번호 인증 내역 생성
    public VerificationRecord(String phoneNumber, String code) {
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}