package com.example.paymentIntegration.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    private String id;
    private String emailAddress;
    private BigDecimal amount;
    private LocalDateTime datePaid;
}
