package com.example.billingservice.consumer;

import com.example.billingservice.api.BillDebitFaultEvent;
import com.example.billingservice.api.BillDebitSuccessEvent;
import com.example.billingservice.api.BillService;
import com.example.billingservice.api.DebitRequestDto;
import com.example.billingservice.api.OrderCreateEvent;
import com.example.billingservice.exception.BillValueLessNilException;
import com.example.billingservice.producer.BillDebitFaultProducer;
import com.example.billingservice.producer.BillDebitSuccessProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final ObjectMapper mapper;
    private final BillService billService;
    private final BillDebitSuccessProducer billDebitSuccessProducer;
    private final BillDebitFaultProducer billDebitFaultProducer;
    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topics.order-created}")
    public void consume(String message) {
        OrderCreateEvent orderCreateEvent = null;
        try {
            orderCreateEvent = mapper.readValue(message, OrderCreateEvent.class);
            billService.debitFromAccount(orderCreateEvent.getAccountGuid(),
                    buildDebitRequestDto(orderCreateEvent));
            billDebitSuccessProducer.send(buildBillDebitSuccessEvent(orderCreateEvent));
        } catch (JsonProcessingException e) {
            log.error("Error parsing message: {}", message, e);
        } catch (BillValueLessNilException e) {
            log.error("Error debit from account. {}, message: {}", e.getMessage(), message);
            if (orderCreateEvent != null) {
                billDebitFaultProducer.send(buildBillDebitFaultEvent(orderCreateEvent));
            }
        }
    }

    private DebitRequestDto buildDebitRequestDto(OrderCreateEvent orderCreateEvent) {
        DebitRequestDto debitRequestDto = new DebitRequestDto();
        debitRequestDto.setValue(orderCreateEvent.getPrice());
        return debitRequestDto;
    }

    private BillDebitFaultEvent buildBillDebitFaultEvent(OrderCreateEvent orderCreateEvent) {
        return BillDebitFaultEvent.builder()
                .userAccountGuid(orderCreateEvent.getAccountGuid())
                .orderGuid(orderCreateEvent.getOrderGuid())
                .build();
    }

    private BillDebitSuccessEvent buildBillDebitSuccessEvent(OrderCreateEvent orderCreateEvent) {
        return BillDebitSuccessEvent.builder()
                .userAccountGuid(orderCreateEvent.getAccountGuid())
                .orderGuid(orderCreateEvent.getOrderGuid())
                .price(orderCreateEvent.getPrice())
                .build();
    }
}
