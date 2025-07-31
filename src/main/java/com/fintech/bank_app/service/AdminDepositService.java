package com.fintech.bank_app.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.fintech.bank_app.Dao.CustomerDao;
import com.fintech.bank_app.Dao.TransactionDao;
import com.fintech.bank_app.Dto.AdminDepositRequest;
import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.exceptions.InvalidTransferException;
import com.fintech.bank_app.exceptions.ResourceNotFoundException;
import com.fintech.bank_app.models.Admin;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;
import com.fintech.bank_app.models.TransactionType;
import com.fintech.bank_app.utils.TransactionUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AdminDepositService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TransactionDao transactionDao;

    @Transactional
    public ApiResponse depositToCustomer(AdminDepositRequest request, Admin admin) {
        Customer customer = customerDao.findByAccountNumber(request.getAccountNumber())
            .orElseThrow(() -> new ResourceNotFoundException("Customer with account number not found"));

        BigDecimal amount = request.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransferException("Deposit amount must be greater than zero");
        }

        customer.setAccountBalance(customer.getAccountBalance().add(amount));
        customerDao.save(customer);

        Transaction txn = TransactionUtil.createTransaction(
            TransactionType.CREDIT,
            amount,
            "Admin-funded deposit",
            null,
            customer
        );
        transactionDao.save(txn);

        return new ApiResponse(true, "Deposit successful");
    }

}
