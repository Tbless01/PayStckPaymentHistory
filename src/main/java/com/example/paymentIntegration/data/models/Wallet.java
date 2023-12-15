package com.example.paymentIntegration.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wallet_id", nullable = false)
    private Long id;
    private String walletID;
    private String emailAddress;
    private BigDecimal balance;
    @OneToMany(fetch = FetchType.EAGER)
    private List<TransactionHistory> transactionHistory;
}