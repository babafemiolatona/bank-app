package com.fintech.bank_app.utils;

import com.fintech.bank_app.models.Customer;
import com.fintech.bank_app.models.Transaction;
import com.fintech.bank_app.models.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionUtil {

    public static Transaction createTransaction(
            TransactionType type,
            BigDecimal amount,
            String description,
            Customer sender,
            Customer recipient
    ) {
        Transaction txn = new Transaction();
        txn.setType(type);
        txn.setAmount(amount);
        txn.setDescription(description);
        txn.setSourceAccount(sender != null ? sender.getAccountNumber() : "ADMIN");
        txn.setDestinationAccount(recipient.getAccountNumber());
        txn.setTimestamp(LocalDateTime.now());
        txn.setCustomer(type == TransactionType.DEBIT ? sender : recipient);
        return txn;
    }
}
