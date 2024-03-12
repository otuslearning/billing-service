package com.example.billingservice.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderCreateEvent {
    private String accountGuid;
    private String orderGuid;
    private String productGuid;
    private Integer quantity;
    private Integer price;
    private String status;
    private String date;
}
