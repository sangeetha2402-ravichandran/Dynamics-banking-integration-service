package com.gbank.banking_integration_service.service;

import com.gbank.banking_integration_service.entity.Customer;
import com.gbank.banking_integration_service.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(String customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found: " + customerId));
    }
}