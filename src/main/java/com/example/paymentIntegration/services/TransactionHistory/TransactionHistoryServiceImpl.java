package com.example.paymentIntegration.services.TransactionHistory;

import com.example.paymentIntegration.data.models.TransactionHistory;
import com.example.paymentIntegration.data.repository.TransactionHistoryRepository;
import com.example.paymentIntegration.dtos.response.TransactionHistoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionHistoryServiceImpl implements TransactionHistoryService{
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public TransactionHistory save(TransactionHistory transactionHistory) {
        return transactionHistoryRepository.save(transactionHistory);
    }

    @Override
    public List<TransactionHistoryResponse> findHistoryByEmailAddress(String emailAddress) {
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findTransactionHistoriesByEmailAddress(emailAddress);
        return transactionHistoryList.stream()
                .map(TransactionHistoryServiceImpl::buildTransactionHistoryResponse)
                .toList();
    }

    private static TransactionHistoryResponse buildTransactionHistoryResponse(TransactionHistory transactionHistory) {

        LocalDateTime transactionDate = transactionHistory.getTransactionDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = transactionDate.format(formatter);

        return TransactionHistoryResponse.builder()
                .id(transactionHistory.getId())
                .transactionDate(formattedDate)
                .transactionType(transactionHistory.getTransactionType())
                .amountPaid(transactionHistory.getAmountPaid())
                .emailAddress(transactionHistory.getEmailAddress())
                .build();
    }

    @Override
    public List<TransactionHistory> getAllUsers() {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
