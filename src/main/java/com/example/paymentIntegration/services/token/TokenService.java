package com.example.paymentIntegration.services.token;

import com.example.paymentIntegration.data.models.Token;

import java.util.Optional;

public interface TokenService {
    Token saveToken(Token token);

    Optional<Token> findTokenByUserEmailAddress(String emailAddress);

    Optional<Token> findTokenByJwt(String jwt);
    void deleteExpiredAndRevokedTokens();

}
