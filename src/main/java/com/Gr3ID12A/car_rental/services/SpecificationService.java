package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationDto;
import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationRequest;

import java.util.List;
import java.util.UUID;

public interface SpecificationService {
    List<SpecificationDto> listSpecifications();

    SpecificationDto getSpecification(UUID id);

    SpecificationDto createSpecification(SpecificationRequest specificationRequest);
}
