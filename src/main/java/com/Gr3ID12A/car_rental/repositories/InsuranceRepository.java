package com.Gr3ID12A.car_rental.repositories;

import com.Gr3ID12A.car_rental.domain.entities.InsuranceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InsuranceRepository extends JpaRepository<InsuranceEntity, UUID> {
    Optional<InsuranceEntity> findByRentalId(UUID rentalId);
}
