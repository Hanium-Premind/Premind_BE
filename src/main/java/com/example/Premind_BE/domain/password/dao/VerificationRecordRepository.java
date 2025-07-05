package com.example.Premind_BE.domain.password.dao;

import com.example.Premind_BE.domain.password.domain.VerificationRecord;
import org.springframework.data.repository.CrudRepository;

public interface VerificationRecordRepository extends CrudRepository<VerificationRecord, String> {


}

