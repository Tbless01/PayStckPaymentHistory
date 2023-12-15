package com.example.paymentIntegration.data.repository;

import com.example.paymentIntegration.data.models.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

        Optional<Token> findTokenByUserEmailAddress(String userEmailAddress);

        Optional<Token> findTokenByJwt(String jwt);
        @Modifying
        @Transactional
        @Query("DELETE FROM Token t WHERE t.isExpired = true AND t.isRevoked = true")
        void deleteExpiredAndRevokedTokens();
}
