package com.example.billingservice.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BillDto {
    String accountGuid;
    Integer value;
}
