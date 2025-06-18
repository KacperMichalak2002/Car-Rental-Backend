package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.AuthResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleEntity;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleName;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import com.Gr3ID12A.car_rental.mappers.UserMapper;
import com.Gr3ID12A.car_rental.repositories.RoleRepository;
import com.Gr3ID12A.car_rental.repositories.TokenRepository;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.JWTService;
import com.Gr3ID12A.car_rental.services.RefreshTokenService;
import com.Gr3ID12A.car_rental.services.impl.AuthenticationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private TokenRepository tokenRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private UserMapper userMapper;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JWTService jwtService;
    @Mock private RefreshTokenService refreshTokenService;

    @InjectMocks private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        UserRequest request = TestDataUtil.createTestUserRequest();
        UserEntity userEntity = TestDataUtil.createTestLocalUserEntity();
        RoleEntity role = TestDataUtil.createTestUserRole();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(userMapper.toEntity(any(UserRequest.class))).thenReturn(userEntity);
        when(roleRepository.findByRoleName(RoleName.ROLE_USER)).thenReturn(Optional.of(role));

        assertDoesNotThrow(() -> authenticationService.registerUser(request));

        verify(userRepository).save(userEntity);
    }

    @Test
    void shouldThrowWhenRegisteringExistingEmail() {
        UserRequest request = TestDataUtil.createTestUserRequest();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(TestDataUtil.createTestLocalUserEntity()));

        assertThrows(RuntimeException.class, () -> authenticationService.registerUser(request));
    }

    @Test
    void shouldVerifySuccessfully() {
        UserRequest request = TestDataUtil.createTestUserRequest();
        UserEntity user = TestDataUtil.createTestLocalUserEntity();
        user.setRoles(Set.of(TestDataUtil.createTestUserRole()));

        Authentication mockAuth = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(mockAuth);
        when(mockAuth.isAuthenticated()).thenReturn(true);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(anyString(), any())).thenReturn("jwt-token");
        when(refreshTokenService.generateRefreshToken(anyString(), eq(user))).thenReturn("refresh-token");

        AuthResponse response = authenticationService.verify(request);

        assertEquals("Success", response.getMessage());
        assertEquals("jwt-token", response.getToken());
        assertEquals("refresh-token", response.getRefreshToken());
    }

    @Test
    void shouldReturnFailedWhenUserNotFoundDuringVerify() {
        UserRequest request = TestDataUtil.createTestUserRequest();
        Authentication mockAuth = mock(Authentication.class);

        when(authenticationManager.authenticate(any())).thenReturn(mockAuth);
        when(mockAuth.isAuthenticated()).thenReturn(true);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        AuthResponse response = authenticationService.verify(request);

        assertEquals("Invalid username or password", response.getMessage());
        assertNull(response.getToken());
    }

    @Test
    void shouldHandleInvalidCredentials() {
        UserRequest request = TestDataUtil.createTestUserRequest();

        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException());

        AuthResponse response = authenticationService.verify(request);

        assertEquals("Invalid username or password", response.getMessage());
    }

    @Test
    void shouldRevokeAllUserTokens() {
        UserEntity user = TestDataUtil.createTestLocalUserEntity();
        TokenEntity token = new TokenEntity();
        token.setExpired(false);
        token.setRevoked(false);

        when(tokenRepository.findAllValidTokensByUser(user.getId())).thenReturn(List.of(token));

        authenticationService.revokeAllUserTokens(user);

        assertTrue(token.isExpired());
        assertTrue(token.isRevoked());
        verify(tokenRepository).saveAll(List.of(token));
    }

    @Test
    void shouldHandleValidRefreshToken() {
        String token = "valid-refresh";
        RefreshTokenResponse response = new RefreshTokenResponse("ok", "new-token");

        when(refreshTokenService.refreshAccessToken(token)).thenReturn(response);

        RefreshTokenResponse result = authenticationService.handleRefreshToken(token);

        assertEquals("ok", result.getMessage());
    }

    @Test
    void shouldHandleInvalidRefreshToken() {
        String token = "invalid";
        when(refreshTokenService.refreshAccessToken(token)).thenThrow(new EntityNotFoundException("Not found"));

        RefreshTokenResponse result = authenticationService.handleRefreshToken(token);

        assertEquals("Invalid refresh token", result.getMessage());
    }

    @Test
    void shouldHandleExpiredRefreshToken() {
        String token = "expired";
        when(refreshTokenService.refreshAccessToken(token)).thenThrow(new RuntimeException("Expired"));

        RefreshTokenResponse result = authenticationService.handleRefreshToken(token);

        assertEquals("Refresh token expired", result.getMessage());
    }
}
