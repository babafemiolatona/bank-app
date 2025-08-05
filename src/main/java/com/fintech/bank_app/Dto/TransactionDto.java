package com.fintech.bank_app.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fintech.bank_app.models.TransactionType;

import lombok.Data;

@Data
public class TransactionDto {

    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private String sourceAccount;
    private String destinationAccount;
    private LocalDateTime timestamp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
