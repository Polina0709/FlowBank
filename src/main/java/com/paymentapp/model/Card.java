package com.paymentapp.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Card {
    private int id;
    private int accountId;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cvv;
}

