package com.example.Premind_BE.domain.auth.dao;


import com.example.Premind_BE.domain.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
