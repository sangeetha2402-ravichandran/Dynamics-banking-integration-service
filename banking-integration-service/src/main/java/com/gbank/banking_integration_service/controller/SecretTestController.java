package com.gbank.banking_integration_service.controller;

import com.gbank.banking_integration_service.service.ExternalApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecretTestController {

    private final ExternalApiService externalApiService;

    public SecretTestController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping("/api/test-secret")
    public String testSecret() {
        return externalApiService.isSecretLoaded()
                ? "Secret loaded successfully"
                : "Secret not loaded";
    }
}