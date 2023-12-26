package com.example.paymentIntegration.dtos.request;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BVNDetails {
    private String bvn;
    private String bvnDateOfBirth;
}
