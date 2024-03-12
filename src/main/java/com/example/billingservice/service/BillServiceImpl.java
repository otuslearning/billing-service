package com.example.billingservice.service;

import com.example.billingservice.api.BillDto;
import com.example.billingservice.api.BillService;
import com.example.billingservice.api.DebitRequestDto;
import com.example.billingservice.api.DepositRequestDto;
import com.example.billingservice.api.RequestContextService;
import com.example.billingservice.api.UserCreatedEvent;
import com.example.billingservice.converter.BillConverter;
import com.example.billingservice.domain.Bill;
import com.example.billingservice.exception.BillValueLessNilException;
import com.example.billingservice.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final BillConverter billConverter;
    private final RequestContextService requestContextService;
    private static final String BILL_VALUE_LESS_NIL = "Bill accountGuid: %s value less nil";

    @Override
    @Transactional
    public void createBill(UserCreatedEvent userCreatedEvent) {
        Bill bill = new Bill();
        bill.setUserGuid(userCreatedEvent.getAccountGuid());
        bill.setAccountGuid(UUID.randomUUID().toString());
        billRepository.save(bill);
    }

    @Override
    @Transactional
    public void debitFromAccount(String accountGuid, DebitRequestDto dto) {
        Bill bill = billRepository.findByUserGuid(accountGuid).orElseThrow();
        bill.debit(dto.getValue());
        checkValue(bill);
    }

    @Override
    @Transactional
    public void depositAccount(String accountGuid, DepositRequestDto dto) {
        Bill bill = billRepository.findByUserGuidAndAccountGuid(requestContextService.getRequestContext().getAccountGuid(),
                accountGuid).orElseThrow();
        bill.deposit(dto.getValue());
        checkValue(bill);
    }

    @Override
    public BillDto getBillAccounts() {
        Bill bill = billRepository.findByUserGuid(
                requestContextService.getRequestContext().getAccountGuid()
        ).orElseThrow();
        return billConverter.convert(bill);
    }

    private void checkValue(Bill bill) {
        if (bill.getValue() < 0) {
            throw new BillValueLessNilException(
                    String.format(BILL_VALUE_LESS_NIL, bill.getAccountGuid())
            );
        }
    }
}
