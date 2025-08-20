package com.example.Premind_BE.domain.interview.dto.ai.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoiceSection {
    private int fluency_20;
    private int speed_20;
    private int total_40;
    private String feedback;
}
