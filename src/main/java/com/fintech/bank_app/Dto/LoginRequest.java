package com.fintech.bank_app.Dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}
