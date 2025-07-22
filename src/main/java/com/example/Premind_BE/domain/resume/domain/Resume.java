package com.example.Premind_BE.domain.resume.domain;

import com.example.Premind_BE.domain.job.domain.JobCategory;
import com.example.Premind_BE.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    @Column(length = 20)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Column(length = 20)
    private String company;

    private LocalDateTime createdDate;

    @Builder.Default
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResumeSection> sections = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addSection(ResumeSection section) {
        this.sections.add(section);
        section.setResume(this);
    }
}
