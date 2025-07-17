package com.fintech.bank_app.mapper;

import com.fintech.bank_app.Dto.TransactionDto;
import com.fintech.bank_app.models.Transaction;

public class TransactionMapper {

    public static TransactionDto toDto(Transaction tx) {
        TransactionDto dto = new TransactionDto();
        dto.setType(tx.getType());
        dto.setAmount(tx.getAmount());
        dto.setDescription(tx.getDescription());
        dto.setSourceAccount(tx.getSourceAccount());
        dto.setDestinationAccount(tx.getDestinationAccount());
        dto.setTimestamp(tx.getTimestamp());
        return dto;
    }

}
