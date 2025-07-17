package com.fintech.bank_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.bank_app.Dao.TransactionDao;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    public List<Transaction> getTransactionsForCustomer(Customer customer) {
        return transactionDao.findByCustomerOrderByTimestampDesc(customer);
    }

}
