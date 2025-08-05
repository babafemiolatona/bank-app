package com.fintech.bank_app.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ScheduledTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private BigDecimal amount;
    private String description;
    private LocalDateTime scheduledTime;
    private LocalDateTime nextExecutionTime;

    @Enumerated(EnumType.STRING)
    private RecurrenceType recurrenceType;

    private Integer remainingOccurrences;

    @Enumerated(EnumType.STRING)
    private ScheduledTransferStatus status = ScheduledTransferStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

}
