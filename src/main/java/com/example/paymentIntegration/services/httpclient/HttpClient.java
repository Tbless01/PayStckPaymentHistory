package com.example.paymentIntegration.services.httpclient;

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

            // Set authorization header
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
                // Handle unsuccessful HTTP response
                response.append("GET request failed with response code: ").append(responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            // Handle exceptions or errors during the HTTP request
            e.printStackTrace();
            response.append("Exception occurred: ").append(e.getMessage());
        }
        return response.toString();
    }

}






























