package com.example.paymentIntegration.services.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpClient {

    public String sendPostRequest(String endpoint, String secretKey, String params) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + secretKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(params.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder response = new StringBuilder();

            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            conn.disconnect();
            return response.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String sendGetRequest(String endpoint, String authHeaderKey, String authHeaderValue) {
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty(authHeaderKey, authHeaderValue);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                response.append("GET request failed with response code: ").append(responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            response.append("Exception occurred: ").append(e.getMessage());
        }
        return response.toString();
    }

    public String createDedicatedAccount(int customerId, String createAccountApi,  String secretKey) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(createAccountApi);
            request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + secretKey);

            String requestBody = "{\"customer\":\"" +customerId+ "\", \"preferred_bank\": \"wema-bank\"}";
            StringEntity params = new StringEntity(requestBody);
            request.setEntity(params);

            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                if (statusCode == 200) {
                    return responseBody;
                } else {
                    return "Failed with status code: " + statusCode;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
