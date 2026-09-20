package com.gbank.banking_integration_service.service;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbank.banking_integration_service.entity.FraudCase;
import org.springframework.stereotype.Service;

@Service
public class FraudMessagePublisher {

    private final ServiceBusSenderClient senderClient;
    private final ObjectMapper objectMapper;

    public FraudMessagePublisher(
            ServiceBusSenderClient senderClient,
            ObjectMapper objectMapper) {

        this.senderClient = senderClient;
        this.objectMapper = objectMapper;
    }

    public void publishFraudCase(FraudCase fraudCase) {

        try {

            String json = objectMapper.writeValueAsString(fraudCase);

            ServiceBusMessage message = new ServiceBusMessage(json);

            message.setContentType("application/json");

            if (fraudCase.getCaseId() != null) {
                message.setMessageId(fraudCase.getCaseId());
                message.setCorrelationId(fraudCase.getCaseId());
            }

            senderClient.sendMessage(message);

        } catch (Exception ex) {
            throw new RuntimeException(
                    "Failed to publish fraud case to Service Bus",
                    ex
            );
        }
    }
}