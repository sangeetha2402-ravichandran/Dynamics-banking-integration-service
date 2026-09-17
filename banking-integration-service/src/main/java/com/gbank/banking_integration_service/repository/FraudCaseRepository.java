package com.gbank.banking_integration_service.repository;

import com.gbank.banking_integration_service.entity.FraudCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudCaseRepository
        extends JpaRepository<FraudCase, String> {
}