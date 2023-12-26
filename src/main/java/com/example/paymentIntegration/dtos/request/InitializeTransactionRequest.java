package com.example.paymentIntegration.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitializeTransactionRequest {

    private BigDecimal amount;

    private String email;


    private String reference;

    private String callback_url;

    private int quantity;

    private List<String> channel;

}

