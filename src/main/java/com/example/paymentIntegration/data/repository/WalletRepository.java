package com.example.paymentIntegration.data.repository;

import com.example.paymentIntegration.data.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByEmailAddress(String emailAddress);
}
