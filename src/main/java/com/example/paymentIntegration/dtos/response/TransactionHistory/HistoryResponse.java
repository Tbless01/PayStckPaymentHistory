package com.example.paymentIntegration.dtos.response.TransactionHistory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    @JsonProperty("requestSuccessful")
    private boolean requestSuccessful;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseBody")
    private TransactionHistoryDetails responseBody;
}
