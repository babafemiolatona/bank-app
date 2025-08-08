package com.fintech.bank_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fintech.bank_app.Dao.AdminDao;
import com.fintech.bank_app.Dao.CustomerDao;
import com.fintech.bank_app.Dto.CreateCustomerDto;
import com.fintech.bank_app.exceptions.InvalidCredentialsException;
import com.fintech.bank_app.exceptions.UserAlreadyExistsException;
import com.fintech.bank_app.mapper.CustomerMapper;
import com.fintech.bank_app.models.Admin;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.Dto.BalanceResponse;
import com.fintech.bank_app.Dto.ChangePasswordRequest;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomerAuthService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public ApiResponse registerCustomer(CreateCustomerDto dto) {

        boolean emailExistsInAdmins = adminDao.findByEmail(dto.getEmail()).isPresent();
        boolean emailExistsInCustomers = customerDao.findByEmail(dto.getEmail()).isPresent();

        if (emailExistsInAdmins || emailExistsInCustomers) {
            throw new UserAlreadyExistsException("Email " + dto.getEmail() + " is already in use.");
        }

        boolean phoneExistsInAdmins = adminDao.findByPhoneNumber(dto.getPhoneNumber()).isPresent();
        boolean phoneExistsInCustomers = customerDao.findByPhoneNumber(dto.getPhoneNumber()).isPresent();

        if (phoneExistsInAdmins || phoneExistsInCustomers) {
            throw new UserAlreadyExistsException("Phone number " + dto.getPhoneNumber() + " is already in use.");
        }

        if (!dto.getPhoneNumber().matches("^0[789][01]\\d{8}$")) {
            throw new InvalidCredentialsException("Invalid phone number format. It should start with 07, 08, or 09 and be 11 digits long.");
        }

        Customer customer = CustomerMapper.fromDto(dto);
        customer.setPassword(passwordEncoder.encode(dto.getPassword()));
        customerDao.save(customer);

        try {
            emailService.sendWelcomeEmail(
            customer.getEmail(),
            customer.getFirstName() + " " + customer.getLastName(),
            customer.getAccountNumber(),
            false
        );
        } catch (Exception e) {
            logger.warn("Failed to send welcome email to {}: {}", customer.getEmail(), e.getMessage());
        }

        return new ApiResponse(true, "Customer registered successfully. Account Number: " + customer.getAccountNumber());
    }

    public BalanceResponse getBalance(Customer customer) {
        return new BalanceResponse(
            customer.getAccountNumber(),
            customer.getAccountType(),
            customer.getAccountBalance()
        );
    }

    @Transactional
    public ApiResponse changePassword(ChangePasswordRequest request, UserDetails userDetails){
        if (!passwordEncoder.matches(request.getOldPassword(), userDetails.getPassword())) {
            throw new InvalidCredentialsException("Old password is incorrect");
        }

        if (request.getNewPassword().equals(request.getOldPassword())) {
            throw new InvalidCredentialsException("New password cannot be the same as old password");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidCredentialsException("Passwords do not match");
        }

        String email = userDetails.getUsername();

        if (userDetails instanceof Customer customer) {

            customer = customerDao.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Customer not found with email: " + email));

            customer.setPassword(passwordEncoder.encode(request.getNewPassword()));
            customerDao.save(customer);
        } else if (userDetails instanceof Admin admin) {

            admin = adminDao.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Admin not found with email: " + email));

            admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
            adminDao.save(admin);
        } else {
            throw new IllegalStateException("Unsupported user type for password change");
        }

        return new ApiResponse(true, "Password updated successfully");
    }
}
