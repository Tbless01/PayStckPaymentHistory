package com.example.paymentIntegration.services.monnifyService;

import com.example.paymentIntegration.dtos.request.WalletCreationRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

@Service
public class MonnifyService {
    private final String BASE_URL = "https://sandbox.monnify.com";
    private final String API_KEY = "MK_TEST_0E4D8MB473";
    private final String CLIENT_SECRET = "H4WQYHYU5S409NY2F6C5301SDMMCLBWD";

    public String createWallet(WalletCreationRequest request) {
        HttpHeaders headers = createHeaders();
        request.setWalletReference("ref1684248425966");
        request.setWalletName("Staging Wallet - ref1684248425966");
        HttpEntity<WalletCreationRequest> requestEntity = new HttpEntity<>(request, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    buildUrl(BASE_URL + "/api/v1/disbursements/wallet"),
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                return responseEntity.getStatusCode().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(API_KEY, CLIENT_SECRET, StandardCharsets.UTF_8);
        return headers;
    }

    private String buildUrl(String urlString) {
        return UriComponentsBuilder.fromHttpUrl(urlString).toUriString();
    }
}
