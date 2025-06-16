package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.AuthResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;

public interface AuthenticationService {
    void registerUser(UserRequest userRequest);

    AuthResponse verify(UserRequest userRequest);

    void revokeAllUserTokens(UserEntity user);

    RefreshTokenResponse handleRefreshToken(String refreshToken);
}
