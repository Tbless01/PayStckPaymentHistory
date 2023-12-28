package com.example.paymentIntegration.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {
    @JsonProperty("requestSuccessful")
    private boolean requestSuccessful;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseBody")
    private WalletDetails responseBody;

}