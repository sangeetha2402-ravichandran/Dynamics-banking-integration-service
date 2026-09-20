package com.gbank.banking_integration_service.controller;

import com.gbank.banking_integration_service.entity.Transaction;
import com.gbank.banking_integration_service.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/api/customers/{customerId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsByCustomer(
            @PathVariable String customerId,@RequestHeader(
                    value = "x-correlation-id",
                    required = false
            ) String correlationId) {

        System.out.println("Correlation ID: " + correlationId);
        return ResponseEntity.ok(
                transactionService.getTransactionsByCustomerId(customerId)
        );
    }

    @GetMapping("/api/transactions/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(
            @PathVariable String transactionId) {

        return ResponseEntity.ok(
                transactionService.getTransactionById(transactionId)
        );
    }
}