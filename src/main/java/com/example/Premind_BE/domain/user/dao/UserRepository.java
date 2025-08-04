package com.example.Premind_BE.domain.user.dao;

import com.example.Premind_BE.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);

    Optional<User> findByNameAndPhoneNumber(String name, String phoneNumber);
}
