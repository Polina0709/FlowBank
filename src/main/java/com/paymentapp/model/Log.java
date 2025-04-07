package com.paymentapp.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    private int id;
    private int userId;
    private String action;
    private LocalDateTime timestamp;
}
