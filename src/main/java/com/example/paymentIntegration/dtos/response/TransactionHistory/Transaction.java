package com.example.paymentIntegration.dtos.response.TransactionHistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String transactionId;
    private double amount;
    private String description;
}

