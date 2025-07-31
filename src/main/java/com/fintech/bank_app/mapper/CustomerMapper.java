package com.fintech.bank_app.mapper;

import com.fintech.bank_app.Dto.CreateCustomerDto;
import com.fintech.bank_app.Dto.CustomerDto;
import com.fintech.bank_app.models.Customer;

public class CustomerMapper {

    public static CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setDateOfBirth(customer.getDateOfBirth());
        dto.setEmail(customer.getEmail());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setAddress(customer.getAddress());
        dto.setAccountNumber(customer.getAccountNumber());
        dto.setAccountBalance(customer.getAccountBalance());
        dto.setAccountType(customer.getAccountType());
        dto.setCreatedAt(customer.getCreatedAt());
        dto.setUpdatedAt(customer.getUpdatedAt());
        
        return dto;
    }

    public static Customer fromDto(CreateCustomerDto dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setDateOfBirth(dto.getDateOfBirth());
        customer.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setAddress(dto.getAddress());
        customer.setAccountType(dto.getAccountType());
        return customer;
    }

}
