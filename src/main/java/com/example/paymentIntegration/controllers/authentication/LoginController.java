package com.example.paymentIntegration.controllers.authentication;


import com.example.paymentIntegration.dtos.request.LoginRequest;
import com.example.paymentIntegration.services.authentication.LoginService;
import com.example.paymentIntegration.utils.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "*")
public class LoginController {
    private final LoginService loginService;

    @SneakyThrows
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        ApiResponse response = loginService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
