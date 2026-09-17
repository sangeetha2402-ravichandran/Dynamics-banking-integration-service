package com.gbank.banking_integration_service.controller;

import com.gbank.banking_integration_service.entity.Customer;
import com.gbank.banking_integration_service.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomer(
            @PathVariable String customerId) {

        return ResponseEntity.ok(
                customerService.getCustomer(customerId)
        );
    }
}