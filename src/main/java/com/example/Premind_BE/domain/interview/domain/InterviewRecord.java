package com.example.Premind_BE.domain.interview.domain;

import com.example.Premind_BE.domain.job.domain.JobCategory;
import com.example.Premind_BE.domain.portfolio.domain.Portfolio;
import com.example.Premind_BE.domain.resume.domain.Resume;
import com.example.Premind_BE.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "interview_records")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_record_id")
    private Long id;

    // 직무 대분류
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_major_id")
    private JobCategory jobMajor;

    // 직무 중분류
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_middle_id")
    private JobCategory jobMiddle;

    // 직무 소분류
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_minor_id")
    private JobCategory jobMinor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interviewee_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_interview_record_id")
    private InterviewRecord reviewInterviewRecord;

    @Enumerated(value = EnumType.STRING)
    private InterviewModeType interviewModeType;

    @Column(name = "question_num")
    private int questionNum;

    @Enumerated(value = EnumType.STRING)
    private InterviewerStyle interviewerStyle;


    @Builder.Default
    @OneToMany(mappedBy = "interviewRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    List<InterviewRecordType> interviewRecordTypes = new ArrayList<>();

    public void addInterviewRecordType(InterviewRecordType type) {
        interviewRecordTypes.add(type);
        type.saveInterviewRecord(this);
    }

    public void removeInterviewRecordType(InterviewRecordType type) {
        interviewRecordTypes.remove(type);
        type.saveInterviewRecord(null);
    }

}
