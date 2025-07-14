package com.fintech.bank_app.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        if (this.accountNumber == null) {
            String prefix = String.valueOf(System.currentTimeMillis()).substring(9, 12);
            String random = String.format("%07d", (int)(Math.random() * 1_000_0000));
            this.accountNumber = prefix + random;
        }

        if (this.accountBalance == null) {
            this.accountBalance = BigDecimal.ZERO;
        }

        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}
