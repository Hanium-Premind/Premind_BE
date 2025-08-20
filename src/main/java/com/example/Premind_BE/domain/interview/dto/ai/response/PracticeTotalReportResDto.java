package com.example.Premind_BE.domain.interview.dto.ai.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PracticeTotalReportResDto {
    private int total_100; // 최종 점수
    private String summary; // 평가 요약
    private VoiceSection voice; // 음성관련 항목
    private GazeSection gaze; // 시선처리 관련 항목
    private Content content; // 답변 내용 관련 항목
    private String jobName; // 직무
    private int totalQuestion; // 문항 개수
    private Double totalTime; // 총 소요시간
}
