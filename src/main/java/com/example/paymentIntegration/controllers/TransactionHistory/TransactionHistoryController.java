package com.example.paymentIntegration.controllers.TransactionHistory;

import com.example.paymentIntegration.dtos.response.TransactionHistoryResponse;
import com.example.paymentIntegration.services.TransactionHistory.TransactionHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transaction/")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class TransactionHistoryController {
    private final TransactionHistoryService transactionHistoryService;

    @GetMapping("history/{emailAddress}")
    public List<TransactionHistoryResponse> getAllTransactionsByEmail(@PathVariable String emailAddress) {
        return transactionHistoryService.findHistoryByEmailAddress(emailAddress);
    }
}
