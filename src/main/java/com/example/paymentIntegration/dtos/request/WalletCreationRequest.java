package com.example.paymentIntegration.dtos.request;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletCreationRequest {
    private String walletReference;
    private String walletName;
    private String customerName;
    private String customerEmail;
    private BVNDetails bvnDetails;
}
