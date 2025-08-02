package com.fintech.bank_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.bank_app.Dto.TransactionDto;
import com.fintech.bank_app.mapper.TransactionMapper;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;
import com.fintech.bank_app.service.TransactionService;

@RestController
@PreAuthorize("hasRole('CUSTOMER')")
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<TransactionDto>> getTransactionHistory(
            @AuthenticationPrincipal Customer customer,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<Transaction> transactions = transactionService.getTransactionsForCustomer(customer, pageable); 
        Page<TransactionDto> dtoPage = transactions.map(TransactionMapper::toDto);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable Long id, @AuthenticationPrincipal Customer customer
    ) {
        Transaction transaction = transactionService.getTransactionById(id, customer);
        TransactionDto dto = TransactionMapper.toDto(transaction);
        return ResponseEntity.ok(dto);
    }

}
