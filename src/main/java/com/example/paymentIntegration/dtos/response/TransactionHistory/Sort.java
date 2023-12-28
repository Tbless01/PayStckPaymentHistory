package com.example.paymentIntegration.dtos.response.TransactionHistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sort {
    private boolean sorted;
    private boolean unsorted;
    private boolean empty;
}