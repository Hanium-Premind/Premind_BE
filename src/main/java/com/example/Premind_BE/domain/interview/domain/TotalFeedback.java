package com.example.Premind_BE.domain.interview.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "total_feedback")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "total_feedback_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private InterviewRecord interviewRecord;

    private int totalScore;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private int voiceFluency;
    private int voiceSpeed;
    private int voiceTotal;
    @Column(columnDefinition = "TEXT")
    private String voiceFeedback;

    private int gazeEye;
    private int gazeExpression;
    private int gazeTotal;
    @Column(columnDefinition = "TEXT")
    private String gazeFeedback;

    private int contentAppropriateness;
    @Column(columnDefinition = "TEXT")
    private String contentFeedback;

    private String jobName; // 직무
    private int totalQuestion; // 문항 개수
    private Double totalTime; // 총 소요시간
}
