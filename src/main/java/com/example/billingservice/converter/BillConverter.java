package com.example.billingservice.converter;

import com.example.billingservice.api.BillDto;
import com.example.billingservice.domain.Bill;
import org.springframework.stereotype.Component;

@Component
public class BillConverter {
    public BillDto convert(Bill model) {
        return BillDto.builder()
                .accountGuid(model.getAccountGuid())
                .value(model.getValue())
                .build();
    }
}
