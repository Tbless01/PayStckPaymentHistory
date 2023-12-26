package com.example.paymentIntegration.services.payment;

import com.example.paymentIntegration.data.models.Payment;
import com.example.paymentIntegration.data.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }
}
