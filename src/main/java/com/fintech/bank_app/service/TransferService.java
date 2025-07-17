package com.fintech.bank_app.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.bank_app.Dao.CustomerDao;
import com.fintech.bank_app.Dao.TransactionDao;
import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.Dto.TransferRequest;
import com.fintech.bank_app.exceptions.InsufficientBalanceException;
import com.fintech.bank_app.exceptions.ResourceNotFoundException;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;
import com.fintech.bank_app.models.TransactionType;

@Service
public class TransferService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TransactionDao transactionDao;

    @Transactional
    public ApiResponse transferFunds(TransferRequest request, Customer sender) {
        Customer recipient = customerDao.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));

        if (sender.getAccountBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        sender.setAccountBalance(sender.getAccountBalance().subtract(request.getAmount()));
        recipient.setAccountBalance(recipient.getAccountBalance().add(request.getAmount()));

        customerDao.save(sender);
        customerDao.save(recipient);

        Transaction debit = createTransaction(TransactionType.DEBIT, request.getAmount(), request.getDescription(), sender, recipient);
        Transaction credit = createTransaction(TransactionType.CREDIT, request.getAmount(), request.getDescription(), sender, recipient);

        transactionDao.save(debit);
        transactionDao.save(credit);

        return new ApiResponse(true, "Transfer successful");
    }

    private Transaction createTransaction(
            TransactionType type, BigDecimal amount, String description,
            Customer sender, Customer recipient
    ) {
        Transaction txn = new Transaction();
        txn.setType(type);
        txn.setAmount(amount);
        txn.setDescription(description);
        txn.setSourceAccount(sender.getAccountNumber());
        txn.setDestinationAccount(recipient.getAccountNumber());
        txn.setTimestamp(LocalDateTime.now());
        txn.setCustomer(type == TransactionType.DEBIT ? sender : recipient);
        return txn;
    }
}
