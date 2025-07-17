package com.fintech.bank_app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCustomerOrderByTimestampDesc(Customer customer);
    
}
