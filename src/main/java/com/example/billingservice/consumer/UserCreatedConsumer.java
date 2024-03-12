package com.example.billingservice.consumer;

import com.example.billingservice.api.BillService;
import com.example.billingservice.api.UserCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreatedConsumer {

    private final ObjectMapper mapper;
    private final BillService billService;
    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topics.user-created}")
    public void consume(String message) {
        try {
            UserCreatedEvent userCreatedEvent = mapper.readValue(message, UserCreatedEvent.class);
            billService.createBill(userCreatedEvent);
        } catch (JsonProcessingException e) {
            log.error("Error parsing message: {}", message, e);
        }
    }
}
