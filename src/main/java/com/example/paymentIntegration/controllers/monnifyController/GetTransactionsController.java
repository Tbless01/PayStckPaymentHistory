package com.example.paymentIntegration.controllers.monnifyController;

import com.example.paymentIntegration.dtos.response.TransactionHistory.Transaction;
import com.example.paymentIntegration.dtos.response.WalletResponse;
import com.example.paymentIntegration.services.monnifyService.MonnifyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/wallet")
@CrossOrigin(origins = "*")
public class GetTransactionsController {

    private final MonnifyService monnifyService;

    @GetMapping("/transactions/{accountNumber}")
    public ResponseEntity<List<Transaction>> getWalletTransactions(@PathVariable String accountNumber) {
        try {
            List<Transaction> transactions = monnifyService.getWalletTransactions(accountNumber);
            if (transactions == null || transactions.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(transactions);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/{walletReference}")
    public ResponseEntity<WalletResponse> getWalletDetails(@PathVariable String walletReference) {
        try {
            WalletResponse walletResponse = monnifyService.getWalletDetails(walletReference);
            if (walletResponse != null) {
                return ResponseEntity.ok(walletResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new WalletResponse());         }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new WalletResponse());
        }
    }
}
