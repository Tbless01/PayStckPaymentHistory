package com.example.paymentIntegration.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class PaystackConfig {

    @Value("${paystack.api.key}")
    private String apiKey;

    @Bean
    public CloseableHttpClient httpClients() {
        return HttpClients.createDefault();
    }

    @Bean
    public HttpHeaders paystackHeadersValidate() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
