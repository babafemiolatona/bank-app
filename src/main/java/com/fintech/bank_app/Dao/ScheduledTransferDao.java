package com.fintech.bank_app.Dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintech.bank_app.models.ScheduledTransfer;
import com.fintech.bank_app.models.ScheduledTransferStatus;

public interface ScheduledTransferDao extends JpaRepository<ScheduledTransfer, Long> {

    List<ScheduledTransfer> findByStatusAndNextExecutionTimeBefore(ScheduledTransferStatus status, LocalDateTime now);

}
