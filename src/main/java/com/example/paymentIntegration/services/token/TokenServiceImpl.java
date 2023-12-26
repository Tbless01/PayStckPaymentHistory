//package com.example.paymentIntegration.services.token;
//
//import com.example.paymentIntegration.data.models.Token;
//import com.example.paymentIntegration.data.repository.TokenRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@AllArgsConstructor
//public class TokenServiceImpl implements TokenService{
//    private final TokenRepository tokenRepository;
//    @Override
//    public Token saveToken(Token token) {
//        return tokenRepository.save(token);
//    }
//
//    @Override
//    public Optional<Token> findTokenByUserEmailAddress(String emailAddress) {
//        return tokenRepository.findTokenByUserEmailAddress(emailAddress);
//    }
//
//    @Override
//    public Optional<Token> findTokenByJwt(String jwt) {
//        return tokenRepository.findTokenByJwt(jwt);
//    }
//    public void deleteExpiredAndRevokedTokens() {
//        tokenRepository.deleteExpiredAndRevokedTokens();
//    }
//}
