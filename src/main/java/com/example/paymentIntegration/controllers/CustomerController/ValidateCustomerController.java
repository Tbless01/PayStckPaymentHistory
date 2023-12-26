package com.example.paymentIntegration.controllers.CustomerController;

import com.example.paymentIntegration.dtos.request.ValidateCustomerRequest;
import com.example.paymentIntegration.services.PaystackService.PaystackService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin("*")
@AllArgsConstructor
public class ValidateCustomerController {
    private final PaystackService paystackService;

    @PostMapping("/validate")
    public String validateCustomer(@RequestBody ValidateCustomerRequest validateCustomerRequest) {
        return paystackService.validateCustomer(validateCustomerRequest);
    }
}
