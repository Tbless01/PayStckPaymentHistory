package com.example.paymentIntegration.services.authentication;


import com.example.paymentIntegration.data.models.Token;
import com.example.paymentIntegration.data.models.User;
import com.example.paymentIntegration.dtos.request.LoginRequest;
import com.example.paymentIntegration.exceptions.UserLoginException;
import com.example.paymentIntegration.security.JwtService;
import com.example.paymentIntegration.services.token.TokenService;
import com.example.paymentIntegration.services.user.UserService;
import com.example.paymentIntegration.utils.ApiResponse;
import com.example.paymentIntegration.utils.GenerateApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final UserService userService;

//    public ApiResponse login(LoginRequest loginRequest) throws UserLoginException {
//        authenticateUser(loginRequest);
//        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmailAddress());
//        User foundUser = userService.findUserByEmailAddress(loginRequest.getEmailAddress());
//        if(userDetails==null) throw new UserLoginException(GenerateApiResponse.INVALID_CREDENTIALS);
//        String jwt = jwtService.generateToken(userDetails);
//        revokeAllUserToken(loginRequest.getEmailAddress());
//        saveToken(jwt, loginRequest.getEmailAddress());
//        return GenerateApiResponse.okResponse(GenerateApiResponse.BEARER +jwt);
//    }

public ApiResponse login(LoginRequest loginRequest) throws UserLoginException {
    authenticateUser(loginRequest);
    UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmailAddress());
    if (userDetails == null) {
        throw new UserLoginException(GenerateApiResponse.INVALID_CREDENTIALS);
    }
    User foundUser = userService.findUserByEmailAddress(loginRequest.getEmailAddress());
    String jwt = jwtService.generateToken(userDetails);
    revokeAllUserToken(loginRequest.getEmailAddress());
    saveToken(jwt, loginRequest.getEmailAddress());
    return GenerateApiResponse.okResponse(GenerateApiResponse.BEARER + jwt);
}



    private void saveToken(String jwt, String emailAddress) {
        Token token = Token.builder()
                .jwt(jwt)
                .isExpired(false)
                .isRevoked(false)
                .userEmailAddress(emailAddress)
                .build();
        tokenService.saveToken(token);
    }

    private void revokeAllUserToken(String emailAddress) {
        Optional<Token> allUserToken = tokenService.findTokenByUserEmailAddress(emailAddress);
        if(allUserToken.isEmpty()) return;
        allUserToken.
                ifPresent(token -> {
                    token.setRevoked(true);
                    token.setExpired(true);
                    tokenService.saveToken(token);
                });
}

    private void authenticateUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmailAddress(), loginRequest.getPassword()));
    }
}
