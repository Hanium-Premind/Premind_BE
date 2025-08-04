package com.example.Premind_BE.domain.auth.domain;

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
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 30) // 30일 TTL (초)
public class RefreshToken implements Serializable {

    @Id
    private String username;

    private String token;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public RefreshToken(String username, String token) {
        this.username = username;
        this.token = token;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Token 갱신 시, updatedAt 값 업데이트
    public void updateToken(String newToken) {
        this.token = newToken;
        this.updatedAt = LocalDateTime.now();
    }
}
