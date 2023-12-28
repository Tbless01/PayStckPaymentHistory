package com.example.paymentIntegration.controllers.CustomerController;


import com.example.paymentIntegration.services.customerForVirtualAccount.PaystackCustomer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin("*")
@AllArgsConstructor
public class UpdateCustomerController {
    private final PaystackCustomer paystackAPIClient;
    @PutMapping("/update/{code}")
    public ResponseEntity<String> updateCustomer(@PathVariable String code, @RequestBody String phone) {
        try {
            return ResponseEntity.ok(paystackAPIClient.updateCustomer(code, phone));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update customer: " + e.getMessage());
        }
    }
}