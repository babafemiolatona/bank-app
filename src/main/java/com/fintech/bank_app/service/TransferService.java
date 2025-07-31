package com.fintech.bank_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.bank_app.Dao.CustomerDao;
import com.fintech.bank_app.Dao.TransactionDao;
import com.fintech.bank_app.Dto.ApiResponse;
import com.fintech.bank_app.Dto.TransferRequest;
import com.fintech.bank_app.exceptions.InsufficientBalanceException;
import com.fintech.bank_app.exceptions.InvalidTransferException;
import com.fintech.bank_app.exceptions.ResourceNotFoundException;
import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;
import com.fintech.bank_app.models.TransactionType;
import com.fintech.bank_app.utils.TransactionUtil;

@Service
public class TransferService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private TransactionDao transactionDao;

    @Transactional
    public ApiResponse transferFunds(TransferRequest request, Customer sender) {

        if (sender.getAccountNumber().equals(request.getAccountNumber())) {
            throw new InvalidTransferException("Cannot transfer to the same account");
        }

        Customer recipient = customerDao.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Recipient not found"));

        if (sender.getAccountBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        sender.setAccountBalance(sender.getAccountBalance().subtract(request.getAmount()));
        recipient.setAccountBalance(recipient.getAccountBalance().add(request.getAmount()));

        customerDao.save(sender);
        customerDao.save(recipient);

        Transaction debit = TransactionUtil.createTransaction(TransactionType.DEBIT, request.getAmount(), request.getDescription(), sender, recipient);
        Transaction credit = TransactionUtil.createTransaction(TransactionType.CREDIT, request.getAmount(), request.getDescription(), sender, recipient);

        transactionDao.save(debit);
        transactionDao.save(credit);

        return new ApiResponse(true, "Transfer successful");
    }

}
