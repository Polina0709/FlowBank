package com.paymentapp.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {
    private int id;
    private int userId;
    private String accountNumber;
    private boolean isBlocked;
    private BigDecimal balance; // додано
}
