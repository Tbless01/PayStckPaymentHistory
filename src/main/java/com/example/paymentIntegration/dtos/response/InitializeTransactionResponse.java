package com.example.paymentIntegration.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitializeTransactionResponse {
    private boolean status;
    private String message;
    private Data data;

//    @Getter
//    @Setter
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class Data {
//        private String authorization_url;
//        private String access_code;
//        private String reference;
//    }
}

