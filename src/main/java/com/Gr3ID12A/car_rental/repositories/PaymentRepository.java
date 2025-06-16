package com.Gr3ID12A.car_rental.repositories;

import com.Gr3ID12A.car_rental.domain.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    Optional<PaymentEntity> findBySessionId(String sessionId);
}
