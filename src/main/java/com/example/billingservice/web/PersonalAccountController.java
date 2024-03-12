package com.example.billingservice.web;

import com.example.billingservice.api.BillDto;
import com.example.billingservice.api.BillService;
import com.example.billingservice.api.DebitRequestDto;
import com.example.billingservice.api.DepositRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${application.web.prefix.public}/accounts")
public class PersonalAccountController {
    private final BillService billService;
    @PostMapping("/{accountGuid}/debit")
    public void debitFromAccount(@PathVariable("accountGuid") String accountGuid, @RequestBody DebitRequestDto dto) {
        billService.debitFromAccount(accountGuid, dto);
    }

    @PostMapping("/{accountGuid}/deposit")
    public void depositAccount(@PathVariable("accountGuid") String accountGuid, @RequestBody DepositRequestDto dto) {
        billService.depositAccount(accountGuid, dto);
    }

    @GetMapping
    public BillDto getBillAccount() {
        return billService.getBillAccounts();
    }

}
