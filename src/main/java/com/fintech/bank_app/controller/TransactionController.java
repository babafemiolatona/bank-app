package com.fintech.bank_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.bank_app.Dto.TransactionDto;
import com.fintech.bank_app.mapper.TransactionMapper;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;
import com.fintech.bank_app.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getTransactionHistory(@AuthenticationPrincipal Customer customer) {
        List<Transaction> transactions = transactionService.getTransactionsForCustomer(customer);
        List<TransactionDto> dtos = transactions.stream()
            .map(TransactionMapper::toDto)
            .toList();

        return ResponseEntity.ok(dtos);
    }


}
