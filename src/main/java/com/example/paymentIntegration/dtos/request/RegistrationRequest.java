package com.example.paymentIntegration.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private Long id;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String password;
    private String userRole;
    private String phoneNumber;
}
