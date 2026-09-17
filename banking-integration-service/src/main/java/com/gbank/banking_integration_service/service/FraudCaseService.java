package com.gbank.banking_integration_service.service;

import com.gbank.banking_integration_service.entity.FraudCase;
import com.gbank.banking_integration_service.repository.FraudCaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FraudCaseService {

    private final FraudCaseRepository fraudCaseRepository;

    public FraudCaseService(FraudCaseRepository fraudCaseRepository) {
        this.fraudCaseRepository = fraudCaseRepository;
    }

    public FraudCase createFraudCase(FraudCase fraudCase) {

        fraudCase.setCaseId("FRAUD-" + UUID.randomUUID().toString().substring(0, 8));
        fraudCase.setStatus("OPEN");
        fraudCase.setCreatedDate(LocalDateTime.now());

        return fraudCaseRepository.save(fraudCase);
    }

    public FraudCase getFraudCaseById(String caseId) {
        return fraudCaseRepository.findById(caseId)
                .orElseThrow(() ->
                        new RuntimeException("Fraud case not found: " + caseId));
    }
}