package com.example.Premind_BE.domain.job.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "jobcategory")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCategory {

    @Id
    @Column(name = "job_category_id")
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Level level;
}
