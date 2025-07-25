package com.example.Premind_BE.domain.resume.domain;

import com.example.Premind_BE.domain.job.domain.JobCategory;
import com.example.Premind_BE.domain.resume.dto.ResumeUpdateDto;
import com.example.Premind_BE.domain.resume.dto.request.UpdateSection;
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


    public void update(JobCategory major, JobCategory middle, JobCategory minor, ResumeUpdateDto dto) {
        this.jobMajor = major;
        this.jobMiddle = middle;
        this.jobMinor = minor;
        this.title = dto.getTitle();
        this.memo = dto.getMemo();
        this.company = dto.getCompany();

        // 기존 qa리스트 삭제 후 새로운 qa리스트로 업데이트
        this.sections.clear();

        int sq = 1;
        for (UpdateSection sec : dto.getQaList()) {
            ResumeSection section = ResumeSection.builder()
                    .resume(this)
                    .sequence(sq++)
                    .question(sec.getQuestion())
                    .answer(sec.getAnswer())
                    .characterCount(sec.getCharacterCount())
                    .build();
            this.addSection(section);
        }
    }
}
