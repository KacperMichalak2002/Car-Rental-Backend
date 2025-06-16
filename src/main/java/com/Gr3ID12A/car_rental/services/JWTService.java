package com.Gr3ID12A.car_rental.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JWTService {
    String generateToken(String username, List<String> roles);

    String extractUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);
}
