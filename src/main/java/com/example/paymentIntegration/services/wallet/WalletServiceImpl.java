package com.example.paymentIntegration.services.wallet;

import com.example.paymentIntegration.data.models.Wallet;
import com.example.paymentIntegration.data.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService{

    private final WalletRepository walletRepository;
    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Override
    public Optional<Wallet> findByEmailAddress(String emailAddress) {
        return walletRepository.findByEmailAddress(emailAddress);
    }

    @Override
    public void deleteAll() {
        walletRepository.deleteAll();
    }
}
