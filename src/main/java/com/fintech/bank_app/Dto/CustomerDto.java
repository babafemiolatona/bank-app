package com.fintech.bank_app.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String address;
    private String accountType;
    private String accountNumber;
    private BigDecimal accountBalance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
