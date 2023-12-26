package com.example.paymentIntegration.controllers.CustomerController;


import com.example.paymentIntegration.services.createCustomerForVirtualAccount.PaystackCreateCustomer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin("*")
@AllArgsConstructor
public class UpdateCustomerController {

    private final PaystackCreateCustomer paystackAPIClient;

    @PutMapping("/update/{code}")
    public String updateCustomer(@PathVariable String code, @RequestBody String phone) {
        return paystackAPIClient.updateCustomer(code, phone);
    }
}
