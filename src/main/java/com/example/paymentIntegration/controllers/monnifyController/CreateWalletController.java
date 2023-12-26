package com.example.paymentIntegration.controllers.monnifyController;

import com.example.paymentIntegration.dtos.request.WalletCreationRequest;
import com.example.paymentIntegration.services.monnifyService.MonnifyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin("*")
@AllArgsConstructor
public class CreateWalletController {
    private final MonnifyService monnifyService;

    @PostMapping("/account")
    public String createNewAccount(@RequestBody WalletCreationRequest walletCreationRequest) {
        return monnifyService.createWallet(walletCreationRequest);
    }
}