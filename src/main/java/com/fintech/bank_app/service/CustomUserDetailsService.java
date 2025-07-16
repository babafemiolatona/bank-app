package com.fintech.bank_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fintech.bank_app.Dao.CustomerDao;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        return customerDao.findByAccountNumber(accountNumber)
            .map(customer -> new org.springframework.security.core.userdetails.User(
                customer.getAccountNumber(),
                customer.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
            ))
            .orElseThrow(() ->
                new UsernameNotFoundException("Customer not found with account number: " + accountNumber));
    }

}
