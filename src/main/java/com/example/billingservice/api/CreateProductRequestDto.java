package com.example.billingservice.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequestDto {
    private String requestGuid;
    private String productGuid;
    private Integer quantity;
}
