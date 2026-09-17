package com.gbank.banking_integration_service.repository;

import com.gbank.banking_integration_service.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, String> {

    List<Loan> findByCustomerId(String customerId);
}