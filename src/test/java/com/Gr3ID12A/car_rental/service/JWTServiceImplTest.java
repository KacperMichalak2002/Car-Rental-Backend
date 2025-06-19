package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import com.Gr3ID12A.car_rental.repositories.TokenRepository;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.JWTService;
import com.Gr3ID12A.car_rental.services.impl.JWTServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTServiceImplTest {

    @Mock private TokenRepository tokenRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private JWTServiceImpl jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Klucz JWT zakodowany w Base64 (minimum 32 bajty, czyli ≥ 256 bitów)
        String secureKey = Base64.getEncoder().encodeToString("thisisaverysecurekey123456789012".getBytes());
        ReflectionTestUtils.setField(jwtService, "secretKey", secureKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 3600000L); // 1h
    }

    @Test
    void shouldGenerateTokenAndSave() {
        String email = "test@example.com";
        List<String> roles = List.of("ROLE_USER");

        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(tokenRepository.findAllValidTokensByUser(user.getId())).thenReturn(Collections.emptyList());

        String token = jwtService.generateToken(email, roles);

        assertNotNull(token);
        verify(tokenRepository).saveAll(any());
        verify(tokenRepository).save(any());
    }

    @Test
    void shouldExtractUsernameFromToken() {
        String email = "test@example.com";
        List<String> roles = List.of("ROLE_USER");

        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(tokenRepository.findAllValidTokensByUser(user.getId())).thenReturn(Collections.emptyList());

        String token = jwtService.generateToken(email, roles);
        String extracted = jwtService.extractUsername(token);

        assertEquals(email, extracted);
    }

    @Test
    void shouldThrowExceptionForInvalidToken() {
        String invalidToken = "invalid.token.value";
        assertThrows(RuntimeException.class, () -> jwtService.extractUsername(invalidToken));
    }

    @Test
    void shouldValidateToken() {
        String email = "test@example.com";
        List<String> roles = List.of("ROLE_USER");

        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID());
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(tokenRepository.findAllValidTokensByUser(user.getId())).thenReturn(Collections.emptyList());

        String token = jwtService.generateToken(email, roles);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);

        boolean isValid = jwtService.validateToken(token, userDetails);

        assertTrue(isValid);
    }
}
