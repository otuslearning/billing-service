package com.example.billingservice.api;


public interface BillService {
    void createBill(UserCreatedEvent userCreatedEvent);
    void debitFromAccount(String accountGuid, DebitRequestDto dto);
    void depositAccount(String accountGuid, DepositRequestDto dto);
    BillDto getBillAccounts();
}
