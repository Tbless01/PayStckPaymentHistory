package com.example.paymentIntegration.services.createCustomerForVirtualAccount;

import com.example.paymentIntegration.dtos.request.CreateCustomerRequest;
import com.example.paymentIntegration.services.PaystackService.PaystackService;
import com.example.paymentIntegration.services.createAccountRestTemp.PaystackCreateAccount;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class PaystackCreateCustomer {
    private final PaystackCreateAccount paystackCreateAccount;

    private final String CREATE_ACCOUNT_ENDPOINT = "https://api.paystack.co/dedicated_account";
    private static final String API_BASE_URL = "https://api.paystack.co";
    private static final String SECRET_KEY = "sk_test_85c71a3d93757eecc2a2c0059518a955cc5a46bb";

    public String createCustomer(CreateCustomerRequest createCustomerRequest, String createCustomerApi, String secretKey) {
        try {
            String params = String.format("{\"email\": \"%s\", \"first_name\": \"%s\", \"last_name\": \"%s\", \"phone\": \"%s\"}",
                    createCustomerRequest.getEmail(), createCustomerRequest.getFirstName(), createCustomerRequest.getLastName(), createCustomerRequest.getPhone());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + secretKey);

            HttpEntity<String> requestEntity = new HttpEntity<>(params, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(createCustomerApi, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {

//                String responseBody = response.getBody();
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(responseBody);
//                String customerCode = jsonNode.get("data").get("customer_code").asText();
//                return paystackCreateAccount.createDedicatedAccountHttpClient(customerCode, CREATE_ACCOUNT_ENDPOINT, SECRET_KEY);

                return "Customer created successfully." + response.getBody();
            } else {
                return "Failed to create customer. Status code: " + response.getStatusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createCustomerApi;
    }


    public String updateCustomer(String code, String phone) {
        String endpoint = API_BASE_URL + "/customer/" + code;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(SECRET_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("phone", phone);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    endpoint,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                return responseEntity.getStatusCode().toString();
            }
        } catch (HttpClientErrorException e) {
            // Handle specific HTTP error responses
            String responseBody = e.getResponseBodyAsString();
            e.printStackTrace();
            return responseBody;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


//    public String updateCustomer(String code, String phone) {
//        String endpoint = API_BASE_URL + "/customer/" + code;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(SECRET_KEY);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        String requestBody = String.format("{\"phone\": \"%s\"}", phone);
//
//        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
//        RestTemplate restTemplate = new RestTemplate();
//
//        try {
//            ResponseEntity<String> responseEntity = restTemplate.exchange(
//                    endpoint,
//                    HttpMethod.PUT,
//                    requestEntity,
//                    String.class
//            );
//
//            if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                return responseEntity.getBody();
//            } else {
//                return responseEntity.getStatusCode().toString();
//            }
//        } catch (HttpClientErrorException e) {
//            String responseBody = e.getResponseBodyAsString();
//            e.printStackTrace();
//            return responseBody;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return e.getMessage();
//        }
//    }
}