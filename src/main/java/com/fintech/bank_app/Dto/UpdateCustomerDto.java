package com.fintech.bank_app.Dto;

import lombok.Data;

@Data
public class UpdateCustomerDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String accountType;

}
