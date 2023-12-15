package com.example.paymentIntegration.data.repository;

import com.example.paymentIntegration.data.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
