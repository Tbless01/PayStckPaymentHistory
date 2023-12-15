package com.example.paymentIntegration.services.TransactionHistory;

import com.example.paymentIntegration.data.models.TransactionHistory;
import com.example.paymentIntegration.dtos.response.TransactionHistoryResponse;

import java.util.List;

public interface TransactionHistoryService {
    TransactionHistory save(TransactionHistory transactionHistory);
    List<TransactionHistoryResponse> findHistoryByEmailAddress(String emailAddress);
    List<TransactionHistory> getAllUsers();
    void deleteAll();
}
