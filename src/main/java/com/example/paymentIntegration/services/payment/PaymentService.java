package com.example.paymentIntegration.services.payment;

import com.example.paymentIntegration.data.models.Payment;

public interface PaymentService {

    Payment save(Payment payment);
}
