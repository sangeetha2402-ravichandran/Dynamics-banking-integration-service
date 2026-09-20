package com.gbank.banking_integration_service.config;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBusConfig {

    @Value("${spring.cloud.azure.servicebus.namespace}")
    private String namespace;

    @Value("${app.servicebus.fraud-queue}")
    private String fraudQueue;

    @Bean
    public ServiceBusSenderClient fraudServiceBusSenderClient() {

        return new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(namespace + ".servicebus.windows.net")
                .credential(new DefaultAzureCredentialBuilder().build())
                .sender()
                .queueName(fraudQueue)
                .buildClient();
    }
}