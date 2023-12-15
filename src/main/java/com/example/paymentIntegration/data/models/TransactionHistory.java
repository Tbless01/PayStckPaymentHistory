package com.example.paymentIntegration.data.models;

import com.example.paymentIntegration.data.models.Enum.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "history_id", nullable = false)
    private Long id;
    private String emailAddress;
    private LocalDateTime transactionDate;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal amountPaid;
}
