package com.fintech.bank_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.Dto.CustomerDto;
import com.fintech.bank_app.Dto.TransactionDto;
import com.fintech.bank_app.Dto.UpdateCustomerDto;
import com.fintech.bank_app.mapper.CustomerMapper;
import com.fintech.bank_app.mapper.TransactionMapper;
import com.fintech.bank_app.models.Admin;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.service.CustomerService;
import com.fintech.bank_app.service.TransactionService;

import jakarta.validation.Valid;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/admins/customers")
public class AdminCustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers(@AuthenticationPrincipal Admin admin) {
        List<CustomerDto> customers = customerService.getAllCustomers(admin)
            .stream()
            .map(CustomerMapper::toDto)
            .toList();

        return ResponseEntity.ok(customers);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id, @AuthenticationPrincipal Admin admin) {
        Customer customer = customerService.getCustomerById(id, admin);
        CustomerDto dto = CustomerMapper.toDto(customer);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getCustomerTransactions(@PathVariable Long id, @AuthenticationPrincipal Admin admin) {
        List<TransactionDto> transactions = transactionService.getTransactionsByCustomerId(id, admin)
            .stream()
            .map(TransactionMapper::toDto)
            .toList();

        return ResponseEntity.ok(transactions);
    }

    @PatchMapping("{id}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerDto updateDto,
            @AuthenticationPrincipal Admin admin) {
        Customer updatedCustomer = customerService.updateCustomer(id, updateDto, admin);
        CustomerDto dto = CustomerMapper.toDto(updatedCustomer);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long id, @AuthenticationPrincipal Admin admin) {
        customerService.deleteCustomer(id, admin);
        return ResponseEntity.ok(new ApiResponse(true, "Customer deleted successfully"));
    }
}
