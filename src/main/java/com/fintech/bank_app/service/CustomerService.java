package com.fintech.bank_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintech.bank_app.Dao.CustomerDao;
import com.fintech.bank_app.Dto.CreateCustomerDto;
import com.fintech.bank_app.Dto.LoginRequest;
import com.fintech.bank_app.Dto.LoginResponse;
import com.fintech.bank_app.SecurityConfig.JwtUtil;
import com.fintech.bank_app.exceptions.UserAlreadyExistsException;
import com.fintech.bank_app.mapper.CustomerMapper;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.Dto.ApiResponse;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ApiResponse registerCustomer(CreateCustomerDto dto) {
        if (customerDao.findByEmail(dto.getEmail()).isPresent()) {
        throw new UserAlreadyExistsException("Email " + dto.getEmail() + " is already in use.");
    }

    Customer customer = CustomerMapper.fromDto(dto);
    customer.setPassword(passwordEncoder.encode(dto.getPassword()));
    customerDao.save(customer);

    return new ApiResponse(true, "Customer registered successfully. Account Number: " + customer.getAccountNumber());
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        return new LoginResponse(token);
    }
}
