package com.example.Premind_BE.domain.user.dao;

import com.example.Premind_BE.domain.user.domain.InterestJob;
import com.example.Premind_BE.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestJobRepository extends JpaRepository<InterestJob, Long> {
    List<InterestJob> findByUser(User user);
}
