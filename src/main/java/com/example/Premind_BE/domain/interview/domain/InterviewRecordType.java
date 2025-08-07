package com.example.Premind_BE.domain.interview.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interview_record_types")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewRecordType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_record_type_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_record_id")
    private InterviewRecord interviewRecord;

    @Enumerated(value = EnumType.STRING)
    private InterviewType interviewType;

    public void saveInterviewRecord(InterviewRecord interviewRecord) {
        this.interviewRecord = interviewRecord;
    }
}
