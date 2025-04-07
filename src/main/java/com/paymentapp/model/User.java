package com.paymentapp.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private int id;
    private String fullName;
    private String personalNumber;
    private String email;
    private String password;
    private LocalDateTime createdAt;
}

