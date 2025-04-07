package com.paymentapp.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private int id;
    private int senderAccountId;
    private int receiverAccountId;
    private BigDecimal amount;
    private LocalDateTime createdAt;
}

