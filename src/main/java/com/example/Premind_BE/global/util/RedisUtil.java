package com.example.Premind_BE.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    // 저장
    public void set(String key, String value, long minutes) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(minutes));
    }

    // 조회
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 삭제
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    // 존재 여부 확인 (optional)
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}