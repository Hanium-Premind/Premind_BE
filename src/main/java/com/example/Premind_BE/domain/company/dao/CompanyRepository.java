package com.example.Premind_BE.domain.company.dao;

import com.example.Premind_BE.domain.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
