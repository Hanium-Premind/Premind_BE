package com.example.Premind_BE.domain.interview.domain;

import com.example.Premind_BE.domain.job.domain.JobCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interview_qas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewQA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_qa_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_record")
    private InterviewRecord interviewRecord;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

    @Column(nullable = false)
    private int sequence;

    @Column(name = "answer_time")
    private int answerTime;

    @Column(name = "qa_feedback", nullable = false, columnDefinition = "TEXT")
    private String qaFeedback;
}
