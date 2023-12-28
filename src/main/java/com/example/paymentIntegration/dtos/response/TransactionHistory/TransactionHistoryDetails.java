package com.example.paymentIntegration.dtos.response.TransactionHistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryDetails {
    private List<Transaction> content;
    private Pageable pageable;
    private boolean last;
    private int totalPages;
    private int totalElements;
    private Sort sort;
    private boolean first;
    private int numberOfElements;
    private int size;
    private int number;
    private boolean empty;
}