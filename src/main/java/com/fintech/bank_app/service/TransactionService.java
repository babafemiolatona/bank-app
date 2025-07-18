package com.fintech.bank_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.bank_app.Dao.TransactionDao;
import com.fintech.bank_app.exceptions.ResourceNotFoundException;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;
import org.springframework.security.access.AccessDeniedException;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    public List<Transaction> getTransactionsForCustomer(Customer customer) {
        return transactionDao.findByCustomerOrderByTimestampDesc(customer);
    }

    public Transaction getTransactionById(Long id, Customer customer) {
        Transaction transaction = transactionDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        if (!transaction.getCustomer().getId().equals(customer.getId())) {
            throw new AccessDeniedException("You are not authorized to view this transaction");
        }

        return transaction;
    }
}
