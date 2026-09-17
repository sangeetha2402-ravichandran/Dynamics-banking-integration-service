package com.gbank.banking_integration_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "fraud_cases")
public class FraudCase {

    @Id
    private String caseId;

    private String customerId;
    private String transactionId;
    private String reason;
    private String status;
    private LocalDateTime createdDate;

    public FraudCase() {
    }

    public FraudCase(String caseId, String customerId, String transactionId,
                     String reason, String status, LocalDateTime createdDate) {
        this.caseId = caseId;
        this.customerId = customerId;
        this.transactionId = transactionId;
        this.reason = reason;
        this.status = status;
        this.createdDate = createdDate;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}