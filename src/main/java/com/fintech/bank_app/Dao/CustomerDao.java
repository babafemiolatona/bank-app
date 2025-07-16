package com.fintech.bank_app.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintech.bank_app.models.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {

    Optional<Customer> findByAccountNumber(String accountNumber);
    Optional<Customer> findByEmail(String email);

}
