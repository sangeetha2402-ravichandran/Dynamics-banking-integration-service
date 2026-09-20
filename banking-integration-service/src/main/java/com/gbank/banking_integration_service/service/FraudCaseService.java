package com.gbank.banking_integration_service.service;

import com.gbank.banking_integration_service.entity.FraudCase;
import com.gbank.banking_integration_service.repository.FraudCaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FraudCaseService {

    private final FraudCaseRepository fraudCaseRepository;
    private final FraudMessagePublisher fraudMessagePublisher;

    public FraudCaseService(FraudCaseRepository fraudCaseRepository,FraudMessagePublisher fraudMessagePublisher) {
        this.fraudCaseRepository = fraudCaseRepository;
        this.fraudMessagePublisher = fraudMessagePublisher;
    }

    public FraudCase createFraudCase(FraudCase fraudCase) {

        FraudCase savedFraudCase =
                fraudCaseRepository.save(fraudCase);

        fraudMessagePublisher.publishFraudCase(savedFraudCase);

        return savedFraudCase;
    }

    public FraudCase getFraudCaseById(String caseId) {
        return fraudCaseRepository.findById(caseId)
                .orElseThrow(() ->
                        new RuntimeException("Fraud case not found: " + caseId));
    }
}