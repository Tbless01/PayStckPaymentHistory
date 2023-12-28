package com.example.paymentIntegration.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class WalletDetails {
    @JsonProperty("availableBalance")
    private long availableBalance;

    @JsonProperty("ledgerBalance")
    private long ledgerBalance;
}