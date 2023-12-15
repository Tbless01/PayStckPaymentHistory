package com.example.paymentIntegration.dtos.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyTransactionRequest {
    private String reference;
    private String emailAddress;
}
