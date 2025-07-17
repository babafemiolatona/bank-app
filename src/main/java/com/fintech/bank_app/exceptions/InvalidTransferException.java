package com.fintech.bank_app.exceptions;

public class InvalidTransferException extends RuntimeException {

    public InvalidTransferException(String message) {
        super(message);
    }

}
