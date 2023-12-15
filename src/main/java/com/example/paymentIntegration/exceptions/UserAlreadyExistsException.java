package com.example.paymentIntegration.exceptions;

public class UserAlreadyExistsException extends PaymentIntegrationException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
