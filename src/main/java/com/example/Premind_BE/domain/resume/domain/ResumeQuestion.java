package com.example.Premind_BE.domain.resume.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("ResumeQuestion")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeQuestion implements Serializable {
    @Id
    private Long id;
    private String question;
}

