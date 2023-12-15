package com.example.paymentIntegration.dtos.response;


import com.example.paymentIntegration.data.models.Enum.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryResponse {
    private Long id;
    private String emailAddress;
    private String transactionDate;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal amountPaid;
}
