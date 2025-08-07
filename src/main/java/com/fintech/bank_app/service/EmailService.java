package com.fintech.bank_app.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fintech.bank_app.Dto.TransferRequest;
import com.fintech.bank_app.models.Customer;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTransactionAlert(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendDebitAlert(Customer sender, Customer recipient, TransferRequest request) {
        String subject = "Debit Alert - ₦" + request.getAmount();
        String body = generateTransactionMessage(sender.getFirstName(), "debited", request, sender, recipient);
        sendTransactionAlert(sender.getEmail(), subject, body);
    }

    public void sendCreditAlert(Customer recipient, Customer sender, TransferRequest request) {
        String subject = "Credit Alert - ₦" + request.getAmount();
        String body = generateTransactionMessage(recipient.getFirstName(), "credited", request, sender, recipient);
        sendTransactionAlert(recipient.getEmail(), subject, body);
    }

    private String generateTransactionMessage(
        String name, String type, TransferRequest request,
        Customer sender, Customer recipient
    ) {

        String fromName = sender.getFirstName() + " " + sender.getLastName();

        return String.format(
            """
            Dear %s,

            ₦%,.2f has been %s %s your account.

            ▪ Description: %s
            ▪ Amount: ₦%,.2f
            ▪ From: %s
            ▪ Time: %s
            ▪ New Balance: ₦%,.2f

            If you did not authorize this transaction, please contact our support team.

            — BankApp Support
            """,
            name,
            request.getAmount(),
            type,
            type.equals("debited") ? "from" : "to",
            request.getDescription(),
            request.getAmount(),
            fromName,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            type.equals("debited") ? sender.getAccountBalance() : recipient.getAccountBalance()
        );
    }
}
