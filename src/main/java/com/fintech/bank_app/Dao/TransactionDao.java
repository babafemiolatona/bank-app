package com.fintech.bank_app.Dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByCustomerOrderByTimestampDesc(Customer customer, Pageable pageable);
    // Page<Transaction> findByCustomerIdOrderByTimestampDesc(Long id, Pageable pageable);

}
