package com.fintech.bank_app.mapper;

import com.fintech.bank_app.Dto.AdminRegisterRequest;
import com.fintech.bank_app.models.Admin;

public class AdminMapper {

    public static Admin fromDto(AdminRegisterRequest dto) {
        Admin admin = new Admin();
        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setDateOfBirth(dto.getDateOfBirth());
        admin.setEmail(dto.getEmail());
        admin.setPhoneNumber(dto.getPhoneNumber());
        admin.setAddress(dto.getAddress());
        admin.setRole("ROLE_ADMIN");
        return admin;
    }

}
