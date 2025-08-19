package com.example.Premind_BE.domain.interview.dto.ai.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateQAResDto {

    private String job_id;
    private String question;
    private int turn;
    private int total_turns;
    private OffsetDateTime created_at;
}