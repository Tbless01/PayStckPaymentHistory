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
import com.example.paymentIntegration.dtos.request.PaymentRequest;
import com.example.paymentIntegration.exceptions.UserDoesNotException;
import com.example.paymentIntegration.services.TransactionHistory.TransactionHistoryService;
import com.example.paymentIntegration.services.httpclient.HttpClient;
import com.example.paymentIntegration.services.user.UserService;
import com.example.paymentIntegration.services.wallet.WalletService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@AllArgsConstructor
public class PaystackService {
    private final UserService userService;
    private final HttpClient httpClient;
    private final WalletService walletService;
    private final TransactionHistoryService transactionHistoryService;

    private static final String SECRET_KEY = "sk_test_81e1c3d6e6a528aa121afafc73219819328f7472";
    private static final String VERIFY_ENDPOINT = "https://api.paystack.co/transaction/verify/";

    public String initializeTransaction(PaymentRequest paymentRequest) throws UserDoesNotException {
        User userFound = userService.findUserByEmailAddress(paymentRequest.getEmailAddress());
        if (userFound != null) {
            String email = paymentRequest.getEmailAddress();
            BigDecimal amount = paymentRequest.getAmount().multiply(BigDecimal.valueOf(100));
            String endpoint = "https://api.paystack.co/transaction/initialize";
            String params = "{\"email\":\"" + email + "\",\"amount\":\"" + amount + "\"}";
            String response = httpClient.sendPostRequest(endpoint, SECRET_KEY, params);
            return response;
        } else
            throw new UserDoesNotException("User does not exist");
    }

//    public String verifyTransaction(String reference) {
//        String endpoint = VERIFY_ENDPOINT + reference;
//        return httpClient.sendGetRequest(endpoint, "Authorization", "Bearer " + SECRET_KEY);
//
//        String verificationResponse = httpClient.sendGetRequest(endpoint, "Authorization", "Bearer " + SECRET_KEY);
//        // Check if the transaction was successful or failed
//        if (verificationResponse != null && verificationResponse.contains("\"status\":true")) {
//            return "Congratulations, payment successful!";
//        } else {
//            return "Sorry, it failed.";
//        }
//    }

//    public String verifyTransaction(String reference) {
//        String endpoint = VERIFY_ENDPOINT + reference;
//        String verificationResponse = httpClient.sendGetRequest(endpoint, "Authorization", "Bearer " + SECRET_KEY);
//
//        // Check if the response is not null and parse the JSON to get the "status" value from "data"
//        if (verificationResponse != null) {
//            JsonObject jsonResponse = new JsonObject(verificationResponse); // Parse JSON response
//            JsonObject data = jsonResponse.get("data").getAsJsonObject();
//            boolean status = data.get("status").getAsBoolean();
//
//            // Check the status from the response and return appropriate message
//            if (status) {
//                return "Congratulations, payment successful!";
//            } else {
//                return "Sorry, it failed.";
//            }
//        } else {
//            return "Failed to verify transaction.";
//        }
//    }


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
                System.out.println(email + " is the email ooo");
                if ("success".equals(status)) {
                    System.out.println(email);
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






