package com.gbank.banking_integration_service.repository;

import com.gbank.banking_integration_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}