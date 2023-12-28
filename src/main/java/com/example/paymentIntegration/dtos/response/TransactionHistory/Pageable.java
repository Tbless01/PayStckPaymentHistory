package com.example.paymentIntegration.dtos.response.TransactionHistory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pageable {
    private Sort sort;
    private int pageSize;
    private int pageNumber;
    private int offset;
    private boolean paged;
    private boolean unpaged;
}