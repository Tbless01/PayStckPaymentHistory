package com.example.paymentIntegration.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateCustomerRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
}



