package com.Gr3ID12A.car_rental.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private String token;
    private String refreshToken;
}
