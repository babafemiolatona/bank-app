package com.fintech.bank_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.bank_app.Dto.AdminDepositRequest;
import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.Dto.CustomerDto;
import com.fintech.bank_app.Dto.TransactionDto;
import com.fintech.bank_app.Dto.UpdateCustomerDto;
import com.fintech.bank_app.mapper.CustomerMapper;
import com.fintech.bank_app.mapper.TransactionMapper;
import com.fintech.bank_app.models.Admin;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;
import com.fintech.bank_app.service.AdminDepositService;
import com.fintech.bank_app.service.CustomerService;
import com.fintech.bank_app.service.TransactionService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/admins/customers")
public class AdminCustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AdminDepositService adminDepositService;

    @GetMapping
    public ResponseEntity<Page<CustomerDto>> getAllCustomers(
        @AuthenticationPrincipal Admin admin,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
        ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Customer> customersPage = customerService.getAllCustomers(admin, pageable);
        Page<CustomerDto> customers = customersPage.map(CustomerMapper::toDto);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id, @AuthenticationPrincipal Admin admin) {
        Customer customer = customerService.getCustomerById(id, admin);
        CustomerDto dto = CustomerMapper.toDto(customer);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("{id}/transactions")
    public ResponseEntity<Page<TransactionDto>> getCustomerTransactions(
        @PathVariable Long id,
        @AuthenticationPrincipal Admin admin,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
        ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<Transaction> transactionsPage = transactionService.getTransactionsByCustomerId(id, admin, pageable);
        Page<TransactionDto> dtoPage = transactionsPage.map(TransactionMapper::toDto);
        return ResponseEntity.ok(dtoPage);
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

    @PostMapping("fund-customer")
    public ResponseEntity<ApiResponse> fundCustomer(
            @Valid @RequestBody AdminDepositRequest request,
            @AuthenticationPrincipal Admin admin) {
        ApiResponse response = adminDepositService.depositToCustomer(request, admin);
        return ResponseEntity.ok(response);
    }
}
