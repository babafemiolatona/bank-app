package com.fintech.bank_app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fintech.bank_app.Dao.AdminDao;
import com.fintech.bank_app.Dao.CustomerDao;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private AdminDao adminDao;

    // @Override
    // public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //     return customerDao.findByEmail(email)
    //         .map(customer -> new org.springframework.security.core.userdetails.User(
    //             customer.getEmail(),
    //             customer.getPassword(),
    //             List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
    //         ))
    //         .orElseThrow(() ->
    //             new UsernameNotFoundException("Customer not found with email: " + email));
    // }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerDao.findByEmail(email)
            .map(user -> (UserDetails) user)
            .or(() -> adminDao.findByEmail(email).map(user -> (UserDetails) user))
            .orElseThrow(() ->
                new UsernameNotFoundException("Customer not found with email: " + email));
    }
}
