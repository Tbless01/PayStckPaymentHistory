package com.example.paymentIntegration.controllers.createAccountController;

import com.example.paymentIntegration.services.PaystackService.PaystackService;
import com.example.paymentIntegration.services.httpclient.HttpClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
@AllArgsConstructor
public class CreateAccountController {
    private final PaystackService paystackService;
    private final HttpClient httpClient;

    @PostMapping("/account")
    public ResponseEntity<String> createNewAccount(@RequestBody String createAccountRequest) {
        return new ResponseEntity<>(paystackService.createAccountForCustomerWithRestTempSec(createAccountRequest), HttpStatus.OK);
//        return paystackService.createAccountForCustomerWithRestTemp(createAccountRequest);
//    return paystackService.createAccountForCustomer(createAccountRequest.getCustomer());
//        return paystackService.createDedicatedVirtualAccountIniTrans(createAccountRequest);
    }
}
