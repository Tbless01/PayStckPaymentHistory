package com.example.paymentIntegration.services.wallet;

import com.example.paymentIntegration.data.models.Wallet;

import java.util.Optional;

public interface WalletService{
    Wallet save(Wallet wallet);
    Optional<Wallet> findByEmailAddress(String emailAddress);
    void deleteAll();
}
