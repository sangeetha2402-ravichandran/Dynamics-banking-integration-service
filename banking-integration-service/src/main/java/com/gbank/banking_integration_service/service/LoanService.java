package com.gbank.banking_integration_service.service;

import com.gbank.banking_integration_service.entity.Loan;
import com.gbank.banking_integration_service.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> getLoansByCustomerId(String customerId) {
        return loanRepository.findByCustomerId(customerId);
    }

    public Loan getLoanById(String loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Loan not found: " + loanId));
    }
}