package com.fintech.bank_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.Dto.ScheduledTransferRequestDto;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.service.ScheduledTransferService;

@RestController
@RequestMapping("/api/v1/scheduled-transfers")
public class ScheduledTransferController {

    @Autowired
    private ScheduledTransferService scheduledTransferService;

    @PostMapping
    public ResponseEntity<ApiResponse> scheduleTransfer(
            @RequestBody ScheduledTransferRequestDto dto,
            @AuthenticationPrincipal Customer customer) {
        ApiResponse response = scheduledTransferService.scheduleTransfer(dto, customer);
        return ResponseEntity.ok(response);
    }

}
