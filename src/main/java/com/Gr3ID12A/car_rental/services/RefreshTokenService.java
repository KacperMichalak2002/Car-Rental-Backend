package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenResponse;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;

public interface RefreshTokenService {
    String generateRefreshToken(String email, UserEntity user);

    RefreshTokenResponse refreshAccessToken(String refreshToken);
}
