package com.example.billingservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userGuid;
    private String accountGuid;
    private Integer value = 0;

    public void deposit(Integer value) {
        this.value += value;
    }

    public void debit(Integer value) {
        this.value -= value;
    }
}
