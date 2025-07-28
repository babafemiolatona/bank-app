package com.fintech.bank_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.Dto.ChangePasswordRequest;
import com.fintech.bank_app.service.CustomerAuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class UpdatePasswordController {

    @Autowired
    private CustomerAuthService customerAuthService;

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            ApiResponse response = new ApiResponse(false, "User not authenticated");
            return ResponseEntity.status(401).body(response);
        }
        ApiResponse response = customerAuthService.changePassword(request, userDetails);
        return ResponseEntity.ok(response);
    }

}   
