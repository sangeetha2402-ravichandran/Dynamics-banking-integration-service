package com.gbank.banking_integration_service.controller;

import com.gbank.banking_integration_service.entity.Loan;
import com.gbank.banking_integration_service.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/api/customers/{customerId}/loans")
    public ResponseEntity<List<Loan>> getLoansByCustomer(
            @PathVariable String customerId) {

        return ResponseEntity.ok(
                loanService.getLoansByCustomerId(customerId)
        );
    }

    @GetMapping("/api/loans/{loanId}")
    public ResponseEntity<Loan> getLoanById(
            @PathVariable String loanId) {

        return ResponseEntity.ok(
                loanService.getLoanById(loanId)
        );
    }
}