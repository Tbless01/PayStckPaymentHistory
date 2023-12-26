package com.example.paymentIntegration.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CreateAccountRequest {
    private String customer;
//    private String preferred_bank;
}
