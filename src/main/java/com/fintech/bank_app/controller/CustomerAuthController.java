package com.fintech.bank_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.Dto.BalanceResponse;
import com.fintech.bank_app.Dto.CreateCustomerDto;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.service.CustomerAuthService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerAuthController {

    @Autowired
    private CustomerAuthService customerAuthService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerCustomer(@Valid @RequestBody CreateCustomerDto dto) {
        ApiResponse response = customerAuthService.registerCustomer(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me/balance")
    public ResponseEntity<ApiResponse> checkBalance(@AuthenticationPrincipal Customer customer){
        BalanceResponse balance = customerAuthService.getBalance(customer);
        ApiResponse response = new ApiResponse(true, "Current balance retrieved successfully", balance);
        return ResponseEntity.ok(response);
    }
}
