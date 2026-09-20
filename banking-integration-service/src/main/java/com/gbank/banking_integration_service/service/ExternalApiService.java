package com.gbank.banking_integration_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiService {

    @Value("${external.api.client-secret}")
    private String clientSecret;

    public boolean isSecretLoaded() {
        return clientSecret != null && !clientSecret.isBlank();
    }
}