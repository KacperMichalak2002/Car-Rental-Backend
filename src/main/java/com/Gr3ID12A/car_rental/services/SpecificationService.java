package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.entities.SpecificationEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpecificationService {
    List<SpecificationEntity> listSpecifications();

    Optional<SpecificationEntity> getSpecification(UUID id);

    SpecificationEntity createSpecification(SpecificationEntity specificationToCreate);
}
