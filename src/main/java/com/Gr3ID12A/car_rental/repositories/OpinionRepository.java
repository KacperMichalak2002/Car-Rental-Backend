package com.Gr3ID12A.car_rental.repositories;

import com.Gr3ID12A.car_rental.domain.entities.OpinionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OpinionRepository extends JpaRepository<OpinionEntity, UUID> {
}
