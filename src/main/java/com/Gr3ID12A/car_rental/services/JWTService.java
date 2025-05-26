package com.Gr3ID12A.car_rental.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String generateToken(String username);

    String extractUsername(String token);

    boolean validateToken(String token, UserDetails userDetails);
}
