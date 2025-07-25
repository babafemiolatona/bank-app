package com.fintech.bank_app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fintech.bank_app.Dao.CustomerDao;
import com.fintech.bank_app.Dto.UpdateCustomerDto;
import com.fintech.bank_app.exceptions.ResourceNotFoundException;
import com.fintech.bank_app.models.Admin;
import com.fintech.bank_app.models.Customer;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public Page<Customer> getAllCustomers(Admin admin, Pageable pageable) {
        return customerDao.findAll(pageable);
    }

    public Customer getCustomerById(Long id, Admin admin) {
        return customerDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    public Customer updateCustomer(Long id, UpdateCustomerDto dto, Admin admin) {
        Customer customer = customerDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        if (dto.getFirstName() != null) customer.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) customer.setLastName(dto.getLastName());
        if (dto.getPhoneNumber() != null) customer.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getAddress() != null) customer.setAddress(dto.getAddress());
        if (dto.getAccountType() != null) customer.setAccountType(dto.getAccountType());

        return customerDao.save(customer);
    }

    public void deleteCustomer(Long id, Admin admin) {
        Customer customer = customerDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
            
        customerDao.delete(customer);
    }
}
