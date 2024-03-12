package com.example.billingservice.producer;

import com.example.billingservice.api.BillDebitFaultEvent;
import com.example.billingservice.exception.ConvertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
@RequiredArgsConstructor
public class BillDebitFaultProducer {
    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    public void send(BillDebitFaultEvent message){
        Assert.notNull(message, "message mustn't be null");
        try {
            kafkaTemplate.send("billing-fault", mapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.error("Error convert and send product reserved event: {}", message, e);
            throw new ConvertException(e.getMessage());
        }
    }

}
