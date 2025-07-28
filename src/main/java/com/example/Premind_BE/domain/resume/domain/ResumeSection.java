package com.example.Premind_BE.domain.resume.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Table(name = "resume_sections")
public class ResumeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_section_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    private int sequence;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

    private Integer characterCount;
}
