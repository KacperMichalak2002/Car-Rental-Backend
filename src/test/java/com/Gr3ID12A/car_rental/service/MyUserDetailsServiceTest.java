package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.repositories.UserRepository;
import com.Gr3ID12A.car_rental.services.impl.MyUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MyUserDetailsServiceTest {

    private UserRepository userRepository;
    private MyUserDetailsService myUserDetailsService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        myUserDetailsService = new MyUserDetailsService(userRepository);
    }

    @Test
    void shouldLoadUserByUsernameSuccessfully() {
        String email = "admin@example.com";
        UserEntity user = TestDataUtil.createTestUserEntityWithEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        var userDetails = myUserDetailsService.loadUserByUsername(email);

        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            myUserDetailsService.loadUserByUsername(email);
        });
    }
}
