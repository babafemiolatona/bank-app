package com.fintech.bank_app.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fintech.bank_app.models.RecurrenceType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScheduledTransferRequestDto {

    @NotBlank
    private String accountNumber;

    @NotNull
    private BigDecimal amount;
    
    private String description;

    @NotNull
    private LocalDateTime scheduledTime;

    private RecurrenceType recurrenceType = RecurrenceType.NONE;

    private Integer occurrences;
}
