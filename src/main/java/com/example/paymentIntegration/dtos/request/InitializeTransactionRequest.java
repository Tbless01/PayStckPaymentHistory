package com.example.paymentIntegration.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitializeTransactionRequest {

//    @Digits(integer = 9, fraction = 0)
    private BigDecimal amount;

    private String email;

//    private String plan;

    private String reference;

//    private String subaccount;

    private String callback_url;

    private int quantity;

//    private Integer invoice_limit;

//    private MetaData metadata;

//    private Integer transaction_charge;

    private List<String> channel;

}

