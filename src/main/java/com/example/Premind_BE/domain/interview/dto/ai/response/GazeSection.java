package com.example.Premind_BE.domain.interview.dto.ai.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GazeSection {
    private int eye_10;
    private int expression_10;
    private int total_20;
    private String feedback;
}
