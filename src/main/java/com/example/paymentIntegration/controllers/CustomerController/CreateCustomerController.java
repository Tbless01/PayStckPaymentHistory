package com.example.paymentIntegration.controllers.CustomerController;

import com.example.paymentIntegration.dtos.request.CreateAccountRequest;
import com.example.paymentIntegration.dtos.request.CreateCustomerRequest;
import com.example.paymentIntegration.services.PaystackService.PaystackService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin("*")
@AllArgsConstructor
public class CreateCustomerController {
    private final PaystackService paystackService;

    @PostMapping("/create")
    public String createNewCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        return paystackService.createCustomerForCustomerVirtualAccount(createCustomerRequest);
    }
    @GetMapping("/{customerCode}")
    public ResponseEntity<String> fetchCustomer(@PathVariable String customerCode) {
        String customerInfo = paystackService.fetchCustomer(customerCode);
        if (customerInfo != null) {
            return ResponseEntity.ok(customerInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
