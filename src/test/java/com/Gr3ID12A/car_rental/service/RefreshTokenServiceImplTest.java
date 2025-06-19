package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenResponse;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.RefreshTokenEntity;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleEntity;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleName;
import com.Gr3ID12A.car_rental.repositories.RefreshTokenRepository;
import com.Gr3ID12A.car_rental.services.JWTService;
import com.Gr3ID12A.car_rental.services.impl.RefreshTokenServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RefreshTokenServiceImplTest {

    @Mock private RefreshTokenRepository refreshTokenRepository;
    @Mock private JWTService jwtService;

    @InjectMocks private RefreshTokenServiceImpl refreshTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        refreshTokenService = new RefreshTokenServiceImpl(refreshTokenRepository, jwtService);
        ReflectionTestUtils.setField(refreshTokenService, "refreshTokenExpiration", 3600L);
    }

    @Test
    void shouldGenerateRefreshToken() {
        UserEntity user = TestDataUtil.createTestUserWithRole();

        String token = refreshTokenService.generateRefreshToken(user.getEmail(), user);

        assertNotNull(token);
        verify(refreshTokenRepository).save(any(RefreshTokenEntity.class));
    }

    @Test
    void shouldRefreshAccessTokenSuccessfully() {
        UserEntity user = TestDataUtil.createTestUserWithRole();
        RefreshTokenEntity entity = RefreshTokenEntity.builder()
                .token("valid-token")
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .build();

        when(refreshTokenRepository.findByToken("valid-token")).thenReturn(Optional.of(entity));
        when(jwtService.generateToken(eq(user.getEmail()), anyList())).thenReturn("new-jwt");

        RefreshTokenResponse response = refreshTokenService.refreshAccessToken("valid-token");

        assertEquals("Success", response.getMessage());
        assertEquals("new-jwt", response.getAuthToken());
    }

    @Test
    void shouldThrowWhenTokenNotFound() {
        when(refreshTokenRepository.findByToken("invalid")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> refreshTokenService.refreshAccessToken("invalid"));
    }

    @Test
    void shouldThrowWhenTokenIsExpired() {
        UserEntity user = TestDataUtil.createTestUserWithRole();
        RefreshTokenEntity entity = RefreshTokenEntity.builder()
                .token("expired")
                .user(user)
                .expiresAt(LocalDateTime.now().minusMinutes(1))
                .build();

        when(refreshTokenRepository.findByToken("expired")).thenReturn(Optional.of(entity));

        assertThrows(RuntimeException.class, () -> refreshTokenService.refreshAccessToken("expired"));
        verify(refreshTokenRepository).delete(entity);
    }
}
