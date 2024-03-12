package com.example.billingservice.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestContextDto {
    private String accountGuid;
}
