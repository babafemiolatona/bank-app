package com.fintech.bank_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.bank_app.Dao.CustomerDao;
import com.fintech.bank_app.Dao.TransactionDao;
import com.fintech.bank_app.exceptions.ResourceNotFoundException;
import com.fintech.bank_app.models.Admin;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private CustomerDao customerDao;

    public Page<Transaction> getTransactionsForCustomer(Customer customer, Pageable pageable) {
        return transactionDao.findByCustomerOrderByTimestampDesc(customer, pageable);
    }

    public Transaction getTransactionById(Long id, Customer customer) {
        Transaction transaction = transactionDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        if (!transaction.getCustomer().getId().equals(customer.getId())) {
            throw new AccessDeniedException("You are not authorized to view this transaction");
        }

        return transaction;
    }

    public Page<Transaction> getTransactionsByCustomerId(Long id, Admin admin, Pageable pageable) {

        Customer customer = customerDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        return transactionDao.findByCustomerOrderByTimestampDesc(customer, pageable);
    }
}
