package com.example.paymentIntegration.controllers.authentication;

import com.example.paymentIntegration.dtos.request.RegistrationRequest;
import com.example.paymentIntegration.exceptions.BadNetworkException;
import com.example.paymentIntegration.services.authentication.RegisterService;
import com.example.paymentIntegration.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/register")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @SneakyThrows
    @PostMapping("/user")
    @ResponseBody
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        ApiResponse response;
        try {
            response = registerService.register(registrationRequest);
            if (response.isSuccessful()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.errorResponse("User registration failed: " + response));
            }

        } catch (BadNetworkException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(ApiResponse.errorResponse("Bad network conditions. Please try again."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.errorResponse("Registration failed. Please check your internet connection and try again."));
        }
    }

}
