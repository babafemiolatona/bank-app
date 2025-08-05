package com.fintech.bank_app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fintech.bank_app.Dao.ScheduledTransferDao;
import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.Dto.ScheduledTransferRequestDto;
import com.fintech.bank_app.Dto.TransferRequest;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.RecurrenceType;
import com.fintech.bank_app.models.ScheduledTransfer;
import com.fintech.bank_app.models.ScheduledTransferStatus;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ScheduledTransferService {

    @Autowired
    private ScheduledTransferDao transferDao;

    @Autowired
    private TransferService transferService;

    public ApiResponse scheduleTransfer(ScheduledTransferRequestDto dto, Customer customer) {
        ScheduledTransfer transfer = new ScheduledTransfer();
        transfer.setCustomer(customer);
        transfer.setAccountNumber(dto.getAccountNumber());
        transfer.setAmount(dto.getAmount());
        transfer.setDescription(dto.getDescription());
        transfer.setScheduledTime(dto.getScheduledTime());
        transfer.setNextExecutionTime(dto.getScheduledTime());
        transfer.setRecurrenceType(dto.getRecurrenceType());
        transfer.setRemainingOccurrences(dto.getOccurrences());

        transferDao.save(transfer);

        return new ApiResponse(true, "Transfer scheduled successfully");
    }

    @Scheduled(fixedRate = 60_000) // Every 1 minute
    @Transactional
    public void processScheduledTransfers() {
        List<ScheduledTransfer> transfers = transferDao.findByStatusAndNextExecutionTimeBefore(
            ScheduledTransferStatus.PENDING, LocalDateTime.now()
        );

        for (ScheduledTransfer transfer : transfers) {
            try {
                TransferRequest request = new TransferRequest();
                request.setAccountNumber(transfer.getAccountNumber());
                request.setAmount(transfer.getAmount());
                request.setDescription(transfer.getDescription());

                transferService.transferFunds(
                    request,
                    transfer.getCustomer()
                );

                // Handle recurrence
                if (transfer.getRecurrenceType() == RecurrenceType.NONE ||
                    (transfer.getRemainingOccurrences() != null && transfer.getRemainingOccurrences() <= 1)) {
                    transfer.setStatus(ScheduledTransferStatus.COMPLETED);
                } else {
                    transfer.setNextExecutionTime(getNextExecutionTime(
                        transfer.getNextExecutionTime(), transfer.getRecurrenceType()
                    ));
                    if (transfer.getRemainingOccurrences() != null) {
                        transfer.setRemainingOccurrences(transfer.getRemainingOccurrences() - 1);
                    }
                }
            } catch (Exception e) {
                transfer.setStatus(ScheduledTransferStatus.FAILED);
                e.printStackTrace();
            }
        }
    }

    private LocalDateTime getNextExecutionTime(LocalDateTime current, RecurrenceType type) {
        return switch (type) {
            case MINUTELY -> current.plusMinutes(1);
            case HOURLY -> current.plusHours(1);
            case DAILY -> current.plusDays(1);
            case WEEKLY -> current.plusWeeks(1);
            case MONTHLY -> current.plusMonths(1);
            default -> current;
        };
    }

}
