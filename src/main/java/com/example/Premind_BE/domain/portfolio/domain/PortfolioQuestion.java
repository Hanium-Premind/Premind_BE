package com.example.Premind_BE.domain.portfolio.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("PortfolioQuestion")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioQuestion implements Serializable {
    @Id
    private Long id;
    private String question;
}

