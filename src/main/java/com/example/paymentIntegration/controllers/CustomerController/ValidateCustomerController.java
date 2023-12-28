package com.example.paymentIntegration.controllers.CustomerController;

import com.example.paymentIntegration.dtos.request.ValidateCustomerRequest;
import com.example.paymentIntegration.services.PaystackService.PaystackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin("*")
@AllArgsConstructor
public class ValidateCustomerController {
    private final PaystackService paystackService;
    @PostMapping("/validate")
    public ResponseEntity<String> validateCustomer(@RequestBody ValidateCustomerRequest validateCustomerRequest) {
        try {
            return ResponseEntity.ok(paystackService.validateCustomer(validateCustomerRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to validate customer: " + e.getMessage());
        }
    }
}