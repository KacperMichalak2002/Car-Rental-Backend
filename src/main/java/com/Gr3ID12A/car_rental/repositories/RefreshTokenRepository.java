package com.Gr3ID12A.car_rental.repositories;

import com.Gr3ID12A.car_rental.domain.entities.token.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByToken(String refreshToken);
}
