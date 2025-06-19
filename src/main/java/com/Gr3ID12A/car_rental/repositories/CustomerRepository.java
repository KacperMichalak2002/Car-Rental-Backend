package com.Gr3ID12A.car_rental.repositories;

import com.Gr3ID12A.car_rental.domain.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByUserId(UUID id);
}
