package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationDto;
import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationRequest;
import com.Gr3ID12A.car_rental.domain.entities.SpecificationEntity;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public interface SpecificationService {
    List<SpecificationDto> listSpecifications();

    SpecificationDto getSpecification(UUID id);

    SpecificationDto createSpecification(SpecificationRequest specificationRequest);

    boolean isExist(@NotNull UUID specificationId);

    SpecificationEntity getSpecificationEntityById(@NotNull UUID specificationId);
}
