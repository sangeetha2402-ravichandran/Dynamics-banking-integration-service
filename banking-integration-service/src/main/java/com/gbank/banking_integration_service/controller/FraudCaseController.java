package com.gbank.banking_integration_service.controller;

import com.gbank.banking_integration_service.entity.FraudCase;
import com.gbank.banking_integration_service.service.FraudCaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud-cases")
public class FraudCaseController {

    private final FraudCaseService fraudCaseService;

    public FraudCaseController(FraudCaseService fraudCaseService) {
        this.fraudCaseService = fraudCaseService;
    }

    @PostMapping
    public ResponseEntity<FraudCase> createFraudCase(
            @RequestBody FraudCase fraudCase) {

        return ResponseEntity.ok(
                fraudCaseService.createFraudCase(fraudCase)
        );
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<FraudCase> getFraudCaseById(
            @PathVariable String caseId) {

        return ResponseEntity.ok(
                fraudCaseService.getFraudCaseById(caseId)
        );
    }
}