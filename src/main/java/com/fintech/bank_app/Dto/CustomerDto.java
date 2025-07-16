package com.fintech.bank_app.Dto;

import java.time.LocalDate;

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

}
