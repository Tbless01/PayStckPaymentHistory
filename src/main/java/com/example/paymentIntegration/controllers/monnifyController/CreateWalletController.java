package com.example.paymentIntegration.controllers.monnifyController;
import com.example.paymentIntegration.dtos.request.WalletCreationRequest;
import com.example.paymentIntegration.services.monnifyService.MonnifyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin("*")
@AllArgsConstructor
public class CreateWalletController {
    private final MonnifyService monnifyService;

    @PostMapping("/account")
    public ResponseEntity<String> createNewAccount(@RequestBody WalletCreationRequest walletCreationRequest) {
        try {
            return ResponseEntity.ok(monnifyService.createWallet(walletCreationRequest));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create account: " + e.getMessage());
        }
    }
}
