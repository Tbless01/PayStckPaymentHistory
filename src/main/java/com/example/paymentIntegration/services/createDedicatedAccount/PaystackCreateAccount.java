package com.example.paymentIntegration.services.createDedicatedAccount;

import com.example.paymentIntegration.dtos.request.CreateAccountRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@AllArgsConstructor
public class PaystackCreateAccount {

    private final RestTemplate restTemplate;

    public String createDedicatedAccountRestTemp(CreateAccountRequest createAccountRequest, String createAccountApi, String secretKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(secretKey);

        String requestBody = "{\"customer\": \"" + createAccountRequest.getCustomer() + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createAccountApi,
                HttpMethod.POST,
                requestEntity,
                String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return "Failed with status code: " + response.getStatusCode().value();
        }
    }

    public String createDedicatedAccountRestTempSec(String createAccountRequest, String createAccountApi, String secretKey) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(secretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("{\"customer\": \"%s\"}"
                , createAccountRequest);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("===> "+requestEntity+ " requestBodyJson is converted successfully");
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    createAccountApi,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                return responseEntity.getStatusCode().toString();
            }
        } catch (HttpClientErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            e.printStackTrace();
            System.out.println("Response Body: " + responseBody);
            e.printStackTrace();
            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String createDedicatedAccountHttpClient(String customerCode, String createAccountApi, String secretKey) {
        String requestBodyJson = "{\"customer\": \"" + customerCode + "\"}";

        HttpClient httpClient = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createAccountApi))
                .header("Authorization", "Bearer " + secretKey)
                .header("Content-Type", "application/json")
                .POST(java.net.http.HttpRequest.BodyPublishers.ofString(requestBodyJson))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return String.valueOf(response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}

