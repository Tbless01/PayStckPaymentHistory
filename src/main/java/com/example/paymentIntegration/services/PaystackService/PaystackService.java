//package com.example.paymentIntegration.services.PaystackService;
//
//
//import com.example.paymentIntegration.dtos.request.PaymentRequest;
//import com.example.paymentIntegration.services.httpclient.HttpClient;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//
//@Service
//@AllArgsConstructor
//public class PaystackService {
//    private final HttpClient httpClient;
//    private static final String SECRET_KEY = "sk_test_81e1c3d6e6a528aa121afafc73219819328f7472";
//    private static final String VERIFY_ENDPOINT = "https://api.paystack.co/transaction/verify/";
//
//    public String initializeTransaction(PaymentRequest paymentRequest) {
//        String email = paymentRequest.getEmail();
//        BigDecimal amount = paymentRequest.getAmount();
//        String endpoint = "https://api.paystack.co/transaction/initialize";
////        String secretKey = "sk_test_81e1c3d6e6a528aa121afafc73219819328f7472";
//        String params = "{\"email\":\"" + email + "\",\"amount\":\"" + amount + "\"}";
//
//        String response = httpClient.sendPostRequest(endpoint, SECRET_KEY, params);
//        extractReferenceFromResponse(response);
//        return response;
//    }
//
//    private String extractReferenceFromResponse(String response) {
//        Pattern pattern = Pattern.compile("\"reference\":\"(.*?)\"");
//        Matcher matcher = pattern.matcher(response);
//        if (matcher.find() && matcher.groupCount() >= 1) {
//            return matcher.group(1);
//        }
//        return null;
//    }
//
//    public String verifyTransaction(String reference) {
//        String endpoint = VERIFY_ENDPOINT + reference;
////        return httpClient.sendGetRequest(endpoint, "Authorization", "Bearer " + SECRET_KEY);
//        String verificationResponse = httpClient.sendGetRequest(endpoint, "Authorization", "Bearer " + SECRET_KEY);
//        if (verificationResponse != null && verificationResponse.contains("\"status\":true")) {
//            return "Congratulations, payment successful!";
//        } else {
//            return "Sorry, it failed.";
//        }
//    }
//
//}
//


package com.example.paymentIntegration.services.PaystackService;

import com.example.paymentIntegration.data.models.Enum.TransactionType;
import com.example.paymentIntegration.data.models.TransactionHistory;
import com.example.paymentIntegration.data.models.User;
import com.example.paymentIntegration.data.models.Wallet;
import com.example.paymentIntegration.dtos.request.CreateAccountRequest;
import com.example.paymentIntegration.dtos.request.CreateCustomerRequest;
import com.example.paymentIntegration.dtos.request.PaymentRequest;
import com.example.paymentIntegration.dtos.request.ValidateCustomerRequest;
import com.example.paymentIntegration.exceptions.UserDoesNotException;
import com.example.paymentIntegration.services.TransactionHistory.TransactionHistoryService;
import com.example.paymentIntegration.services.createAccountRestTemp.PaystackCreateAccount;
import com.example.paymentIntegration.services.createCustomerForVirtualAccount.PaystackCreateCustomer;
import com.example.paymentIntegration.services.httpclient.HttpClient;
import com.example.paymentIntegration.services.user.UserService;
import com.example.paymentIntegration.services.wallet.WalletService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class PaystackService {
    private final UserService userService;
    private final HttpClient httpClient;
    private final PaystackCreateCustomer paystackCreateCustomer;
    private final PaystackCreateAccount paystackCreateAccount;
    private final WalletService walletService;
    private final TransactionHistoryService transactionHistoryService;

    private final String SECRET_KEY = "sk_test_92df4c840e3033f629ce899a7bb946935fa749dc";
    private final String VERIFY_ENDPOINT = "https://api.paystack.co/transaction/verify/";
    private final String INITIATE_ENDPOINT =  "https://api.paystack.co/transaction/initialize";
    private final String CREATE_ACCOUNT_ENDPOINT = "https://api.paystack.co/dedicated_account";
    private final String CREATE_CUSTOMER_URL = "https://api.paystack.co/customer";

    private final CloseableHttpClient httpClientValidate;
    private final String FETCH_CUSTOMER_URL =  "https://api.paystack.co/customer/";

    private final HttpHeaders paystackHeadersValidate;
    private final RestTemplate restTemplate;
    private static String API_VALIDATE_URL = "https://api.paystack.co";

    public String initializeTransaction(PaymentRequest paymentRequest) throws UserDoesNotException {
        User userFound = userService.findUserByEmailAddress(paymentRequest.getEmailAddress());
        if (userFound != null) {
            String email = paymentRequest.getEmailAddress();
            BigDecimal amount = paymentRequest.getAmount().multiply(BigDecimal.valueOf(100));
            String params = "{\"email\":\"" + email + "\",\"amount\":\"" + amount + "\"}";
            String response = httpClient.sendPostRequest(INITIATE_ENDPOINT, SECRET_KEY, params);
            return response;
        } else
            throw new UserDoesNotException("User does not exist");
    }


    public String verifyTransaction(String reference) {

        String endpoint = VERIFY_ENDPOINT + reference;
        String verificationResponse = httpClient.sendGetRequest(endpoint, "Authorization", "Bearer " + SECRET_KEY);

        if (verificationResponse != null) {
            try {
                JsonObject jsonResponse = JsonParser.parseString(verificationResponse).getAsJsonObject();
                JsonObject data = jsonResponse.getAsJsonObject("data");
                String status = data.get("status").getAsString();
                String amount = data.get("amount").getAsString();
                BigDecimal creditAmount = new BigDecimal(amount);

                JsonObject json = JsonParser.parseString(verificationResponse).getAsJsonObject();
                String email = json.getAsJsonObject("data").getAsJsonObject("customer").get("email").getAsString();
                if ("success".equals(status)) {
                    Optional<Wallet> foundWallet = walletService.findByEmailAddress(email);
                    BigDecimal currentBalance = foundWallet.get().getBalance();
                    foundWallet.get().setBalance(currentBalance.add(creditAmount.divide(BigDecimal.valueOf(100))));
                    walletService.save(foundWallet.get());
                    buildTransactionHistory(email, creditAmount);
                    return "Congratulations, payment successful!";
                } else if ("bank_authentication".equals(status))
                    return "Bank authentication required for this transaction.";
                else if ("failed".equals(status)) return "Sorry, the transaction was declined.";
                else return "Unknown status for the transaction.";
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed to verify transaction.";
            }
        }
        return null;
    }
    public String createDedicatedVirtualAccountIniTrans(CreateAccountRequest createAccountRequest) {
        String params = "{\"code\":\"" +createAccountRequest.getCustomer() + "\"}";
//       String secretKey = "sk_live_dfe8b8b0b3b0088e0af45f4dfb4790f1ba278b25";
        String result = httpClient.sendPostRequest(CREATE_ACCOUNT_ENDPOINT, SECRET_KEY, params);
        if (result != null) {
            return "Response: " + result;
        } else {
            return "Request failed.";
        }
    }
    public String createCustomerForCustomerVirtualAccount(CreateCustomerRequest createCustomerRequest){
        return paystackCreateCustomer.createCustomer(createCustomerRequest, CREATE_CUSTOMER_URL, SECRET_KEY);
    }
    public String createAccountForCustomer(int customer){
       return httpClient.createDedicatedAccount(customer, CREATE_ACCOUNT_ENDPOINT,SECRET_KEY);
    }

    public String createAccountForCustomerWithRestTemp(CreateAccountRequest createAccountRequest){
       return paystackCreateAccount.createDedicatedAccountRestTemp(createAccountRequest, CREATE_ACCOUNT_ENDPOINT,SECRET_KEY);
    }
    public String createAccountForCustomerWithRestTempSec(String createAccountRequest){
        return paystackCreateAccount.createDedicatedAccountHttpClient(createAccountRequest, CREATE_ACCOUNT_ENDPOINT,SECRET_KEY);
    }


//    public void validateCustomer(String email, String bvn) throws IOException {
//        String endpoint = apiValidateUrl + "/customer/validate";
//
//        // Create a JSON payload for validating a customer
//        JSONObject payload = new JSONObject();
//        payload.put("email", email);
//        payload.put("bvn", bvn);
//
//        // Make a POST request to Paystack API
//        HttpPost request = new HttpPost(endpoint);
//        request.setEntity(new StringEntity(payload.toString()));
//        request.setHeaders(paystackHeaders);
//
//        try (CloseableHttpResponse response = httpClient.execute(request)) {
//            // Handle the response as needed
//            int statusCode = response.getStatusLine().getStatusCode();
//            String responseBody = EntityUtils.toString(response.getEntity());
//            // Process the response, check for errors, etc.
//        }
//    }

    public String validateCustomer(ValidateCustomerRequest validateCustomerRequest) {
        String endpoint = API_VALIDATE_URL + "/customer/validate";

        String requestBody = "{\"email\": \"" + validateCustomerRequest.getEmail() + "\", \"bvn\": \"" + validateCustomerRequest.getBvn() + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(SECRET_KEY);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                requestEntity,
                String.class);

        HttpStatusCode statusCode = response.getStatusCode();

        if (statusCode == HttpStatus.OK) {
            return response.getBody();
        } else {
            return "Error occurred: " + statusCode.value();
        }
    }

    public String fetchCustomer(String customerCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SECRET_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                FETCH_CUSTOMER_URL + customerCode,
                HttpMethod.GET,
                entity,
                String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return "Request failed with status code: " + response.getStatusCode().value();
        }
    }
    private String extractReferenceFromResponse(String response) {
        Pattern pattern = Pattern.compile("\"reference\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(response);
        if (matcher.find() && matcher.groupCount() >= 1) {
            return matcher.group(1);
        }
        return null;
    }

    private void buildTransactionHistory(String email, BigDecimal creditAmount) {
        Optional<Wallet> wallet = walletService.findByEmailAddress(email);
        Wallet foundWallet = wallet.get();
        TransactionHistory newTransaction = new TransactionHistory();
        newTransaction.setEmailAddress(foundWallet.getEmailAddress());
        newTransaction.setTransactionDate(LocalDateTime.now());
        newTransaction.setTransactionType(TransactionType.CREDIT);
        newTransaction.setAmountPaid(creditAmount.divide(BigDecimal.valueOf(100)));

        transactionHistoryService.save(newTransaction);
        List<TransactionHistory> transactionHistory = foundWallet.getTransactionHistory();
        transactionHistory.add(newTransaction);
        foundWallet.setTransactionHistory(transactionHistory);
        walletService.save(foundWallet);
    }
}






