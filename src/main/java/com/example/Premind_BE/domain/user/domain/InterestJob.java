package com.example.Premind_BE.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "InterestJob") // 테이블 이름이 대소문자 구분된다면 정확히 맞춰야 함
public class InterestJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_job_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "job", length = 20, nullable = false)
    private String job;
}
