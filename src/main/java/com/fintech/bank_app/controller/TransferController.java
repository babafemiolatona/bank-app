package com.fintech.bank_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.Dto.TransferRequest;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.service.TransferService;

import jakarta.validation.Valid;

@RestController
@PreAuthorize("hasRole('CUSTOMER')")
@RequestMapping("/api/v1/transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<ApiResponse> transferFunds(
        @Valid @RequestBody TransferRequest transferRequest,
        @AuthenticationPrincipal Customer customer
    ) {
        ApiResponse response = transferService.transferFunds(transferRequest, customer);
        return ResponseEntity.ok(response);
    }

}
