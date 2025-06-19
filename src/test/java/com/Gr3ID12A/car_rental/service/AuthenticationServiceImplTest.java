package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.AuthResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerDto;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.domain.entities.token.TokenEntity;
import com.Gr3ID12A.car_rental.repositories.TokenRepository;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.*;

import com.Gr3ID12A.car_rental.services.impl.AuthenticationServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private CustomerService customerService;

    @Mock
    private UsersService usersService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void verify_ShouldReturnSuccessAuthResponse_WhenCredentialsAreValid() {
        UserRequest request = TestDataUtil.createTestUserRequest();
        UserEntity user = TestDataUtil.createTestUserEntity();
        List<TokenEntity> tokens = TestDataUtil.createTestTokenEntityList(user);
        CustomerDto customerDto = TestDataUtil.createTestCustomerDto(UUID.randomUUID());

        Authentication auth = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(true);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(tokenRepository.findAllValidTokensByUser(user.getId())).thenReturn(tokens);
        when(customerService.getCustomerByUserId(user.getId())).thenReturn(customerDto);
        when(jwtService.generateToken(eq(request.getEmail()), anyList())).thenReturn("access-token");
        when(refreshTokenService.generateRefreshToken(eq(request.getEmail()), eq(user))).thenReturn("refresh-token");

        AuthResponse response = authenticationService.verify(request);

        assertNotNull(response);
        assertEquals("Success", response.getMessage());
        assertEquals("access-token", response.getToken());
        assertEquals("refresh-token", response.getRefreshToken());
        assertEquals(customerDto.getId().toString(), response.getCustomerId());
        verify(tokenRepository).saveAll(anyList());
    }

    @Test
    void registerUser_ShouldCreateUser_WhenEmailNotTaken() {
        UserRequest request = TestDataUtil.createTestUserRequest();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(usersService.createUser(any(UserRequest.class))).thenReturn("Success");

        assertDoesNotThrow(() -> authenticationService.registerUser(request));
        verify(usersService).createUser(any(UserRequest.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailIsTaken() {
        UserRequest request = TestDataUtil.createTestUserRequest();
        UserEntity existingUser = TestDataUtil.createTestUserEntity();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(RuntimeException.class, () -> authenticationService.registerUser(request));
    }

    @Test
    void revokeAllUserTokens_ShouldUpdateAndSaveTokens() {
        UserEntity user = TestDataUtil.createTestUserEntity();
        List<TokenEntity> tokens = TestDataUtil.createTestTokenEntityList(user);

        when(tokenRepository.findAllValidTokensByUser(user.getId())).thenReturn(tokens);

        authenticationService.revokeAllUserTokens(user);

        for (TokenEntity token : tokens) {
            assertTrue(token.isExpired());
            assertTrue(token.isRevoked());
        }

        verify(tokenRepository).saveAll(tokens);
    }

    @Test
    void handleRefreshToken_ShouldReturnNewAccessToken_WhenValid() {
        RefreshTokenResponse expected = new RefreshTokenResponse("Success", "new-access-token");
        when(refreshTokenService.refreshAccessToken("valid-token")).thenReturn(expected);

        RefreshTokenResponse actual = authenticationService.handleRefreshToken("valid-token");

        assertEquals(expected.getMessage(), actual.getMessage());
        assertEquals(expected.getAuthToken(), actual.getAuthToken());
    }

    @Test
    void handleRefreshToken_ShouldReturnInvalid_WhenEntityNotFound() {
        when(refreshTokenService.refreshAccessToken("bad-token"))
                .thenThrow(new EntityNotFoundException("Token not found"));

        RefreshTokenResponse response = authenticationService.handleRefreshToken("bad-token");

        assertEquals("Invalid refresh token", response.getMessage());
        assertNull(response.getAuthToken());
    }

    @Test
    void handleRefreshToken_ShouldReturnExpired_WhenRuntimeExceptionThrown() {
        when(refreshTokenService.refreshAccessToken("expired-token"))
                .thenThrow(new RuntimeException("Expired"));

        RefreshTokenResponse response = authenticationService.handleRefreshToken("expired-token");

        assertEquals("Refresh token expired", response.getMessage());
        assertNull(response.getAuthToken());
    }
}
