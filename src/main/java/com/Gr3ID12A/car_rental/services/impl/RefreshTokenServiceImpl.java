package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenResponse;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.RefreshTokenEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenType;
import com.Gr3ID12A.car_rental.repositories.RefreshTokenRepository;
import com.Gr3ID12A.car_rental.repositories.TokenRepository;
import com.Gr3ID12A.car_rental.services.JWTService;
import com.Gr3ID12A.car_rental.services.RefreshTokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenRepository tokenRepository;

    private final JWTService jwtService;

    @Value("${REFRESH_TOKEN_EXPIRATION}")
    private long refreshTokenExpiration;

    @Override
    public String generateRefreshToken(String email, UserEntity user) {

        String generatedRefreshToken = UUID.randomUUID().toString();

        RefreshTokenEntity refreshTokenToBeSaved = RefreshTokenEntity.builder()
                .user(user)
                .token(generatedRefreshToken)
                .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenExpiration))
                .tokenType(TokenType.REFRESH_TOKEN)
                .build();

        refreshTokenRepository.save(refreshTokenToBeSaved);

        return generatedRefreshToken;
    }

    @Override
    public RefreshTokenResponse refreshAccessToken(String refreshToken) {

        RefreshTokenEntity refreshTokenFound = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new EntityNotFoundException("Invalid refresh token"));

        if(isExpired(refreshTokenFound)){
            refreshTokenRepository.delete(refreshTokenFound);
            throw new RuntimeException("Refresh token expired");
        }

        UserEntity user = refreshTokenFound.getUser();

        List<String> roles = user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .toList();

        String newAccessToken = jwtService.generateToken(user.getEmail(), roles);

        TokenEntity tokenToBeSaved = TokenEntity.builder()
                .user(user)
                .token(newAccessToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(tokenToBeSaved);

        return RefreshTokenResponse.builder()
                .message("Success")
                .authToken(newAccessToken)
                .build();
    }

    private boolean isExpired(RefreshTokenEntity refreshToken){
        return LocalDateTime.now().isAfter(refreshToken.getExpiresAt());
    }
}
