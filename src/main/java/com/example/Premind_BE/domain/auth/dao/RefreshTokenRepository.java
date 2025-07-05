package com.example.Premind_BE.domain.auth.dao;


import com.example.Premind_BE.domain.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    // 이메일을 기준으로 RefreshToken을 찾는 메소드
    Optional<RefreshToken> findByToken(String token);

}
