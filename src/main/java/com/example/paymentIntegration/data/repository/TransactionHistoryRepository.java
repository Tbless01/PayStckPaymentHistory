package com.example.paymentIntegration.data.repository;

import com.example.paymentIntegration.data.models.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    Optional<TransactionHistory> findById(Long aLong);

    List<TransactionHistory> findAll();
    List<TransactionHistory> findTransactionHistoriesByEmailAddress(String emailAddress);

}
