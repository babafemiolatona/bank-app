package com.fintech.bank_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.bank_app.Dto.AdminRegisterRequest;
import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.service.AdminAuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminAuthController {

    @Autowired
    private AdminAuthService adminAuthService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerAdmin(@Valid @RequestBody AdminRegisterRequest request) {
        ApiResponse response = adminAuthService.registerAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
