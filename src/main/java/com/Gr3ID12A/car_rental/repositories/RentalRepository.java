package com.Gr3ID12A.car_rental.repositories;

import com.Gr3ID12A.car_rental.domain.entities.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<RentalEntity, UUID> {
    List<RentalEntity> findAllByCustomer_Id(UUID customerId);
}
