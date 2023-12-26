package com.example.paymentIntegration.services.authentication;


import com.example.paymentIntegration.data.models.User;
import com.example.paymentIntegration.dtos.request.LoginRequest;
import com.example.paymentIntegration.exceptions.UserLoginException;
import com.example.paymentIntegration.security.JwtService;
import com.example.paymentIntegration.services.user.UserService;
import com.example.paymentIntegration.utils.ApiResponse;
import com.example.paymentIntegration.utils.GenerateApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserService userService;

public ApiResponse login(LoginRequest loginRequest) throws UserLoginException {
    Authentication authentication = authenticateUser(loginRequest);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmailAddress());
    if (userDetails == null) {
        throw new UserLoginException(GenerateApiResponse.INVALID_CREDENTIALS);
    }
    User foundUser = userService.findUserByEmailAddress(loginRequest.getEmailAddress());
    String jwt = jwtService.generateToken(userDetails);
    return GenerateApiResponse.okResponse(GenerateApiResponse.BEARER + jwt);
}
    private Authentication authenticateUser(LoginRequest loginRequest) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmailAddress(), loginRequest.getPassword()));
    }
}
