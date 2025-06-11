package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.mappers.UserMapper;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.JWTService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private JWTService jwtService;
    @Mock private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Poprawne logowanie
    @Test
    void shouldReturnTokenWhenAuthenticationIsSuccessful() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("john");
        userRequest.setPassword("password");

        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(jwtService.generateToken("john")).thenReturn("mocked-token");

        String result = userService.verify(userRequest);

        assertEquals("mocked-token", result);
    }

    //  Nieudane logowanie
    @Test
    void shouldReturnFailedWhenAuthenticationFails() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("john");
        userRequest.setPassword("wrongpass");

        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);

        String result = userService.verify(userRequest);

        assertEquals("Failed", result);
    }

    //  Udana rejestracja
    @Test
    void shouldRegisterUserSuccessfully() {
        UserRequest request = new UserRequest();
        request.setUsername("john");
        request.setPassword("password");

        when(userRepository.findByUsername("john")).thenReturn(null);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("john");

        when(userMapper.toEntity(request)).thenReturn(userEntity);

        userService.registerUser(request);

        verify(userRepository).save(userEntity);
    }

    //  Nieudana rejestracja – login zajęty
    @Test
    void shouldFailRegistrationWhenUsernameExists() {
        UserRequest request = new UserRequest();
        request.setUsername("john");
        request.setPassword("password");

        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("john");

        when(userRepository.findByUsername("john")).thenReturn(existingUser);

        assertThrows(RuntimeException.class, () -> userService.registerUser(request));
        verify(userRepository, never()).save(any());
    }
}
