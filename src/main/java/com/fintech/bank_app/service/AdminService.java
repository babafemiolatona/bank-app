package com.fintech.bank_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintech.bank_app.Dao.AdminDao;
import com.fintech.bank_app.Dto.AdminRegisterRequest;
import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.exceptions.UserAlreadyExistsException;
import com.fintech.bank_app.mapper.AdminMapper;
import com.fintech.bank_app.models.Admin;

@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApiResponse registerAdmin(AdminRegisterRequest dto) {
        if (adminDao.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email " + dto.getEmail() + " is already in use.");
        }

        Admin admin = AdminMapper.fromDto(dto);
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        adminDao.save(admin);

        return new ApiResponse(true, "Admin registered successfully.");
    }
}
