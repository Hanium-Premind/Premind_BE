package com.example.Premind_BE.domain.interview.dto.ai.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateQAResDto {
    private String job_id;
    private String question;
    private int turn;
    private int total_turns;
    private String created_at;
}

