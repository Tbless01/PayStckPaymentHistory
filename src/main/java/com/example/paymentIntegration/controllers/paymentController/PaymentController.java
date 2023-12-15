package com.example.paymentIntegration.controllers.paymentController;

import com.example.paymentIntegration.dtos.request.PaymentRequest;
import com.example.paymentIntegration.exceptions.UserDoesNotException;
import com.example.paymentIntegration.services.PaystackService.PaystackService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@CrossOrigin("*")
@AllArgsConstructor
public class PaymentController {
    private final PaystackService paystackService;

    @PostMapping("/initiate-payment")
    public String initializeTransaction(@RequestBody PaymentRequest paymentRequest) throws UserDoesNotException {
        return paystackService.initializeTransaction(paymentRequest);
    }

    @GetMapping("/verify-payment/{reference}")
    public String verifyTransaction(@PathVariable String reference) {
        return paystackService.verifyTransaction(reference);
    }
}
