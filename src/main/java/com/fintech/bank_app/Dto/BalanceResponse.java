package com.fintech.bank_app.Dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BalanceResponse {

    private String accountNumber;
    private String accountType;
    private BigDecimal balance;

    public BalanceResponse(String accountNumber, String accountType, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }

}
