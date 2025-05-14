package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.entities.SpecificationEntity;
import com.Gr3ID12A.car_rental.repositories.SpecificationRepository;
import com.Gr3ID12A.car_rental.services.SpecificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecificationServiceImpl implements SpecificationService {
    private final SpecificationRepository specificationRepository;

    @Override
    public List<SpecificationEntity> listSpecifications() {
        return specificationRepository.findAll();
    }

    @Override
    public Optional<SpecificationEntity> getSpecification(UUID id) {
        return specificationRepository.findById(id);
    }

    @Override
    public SpecificationEntity createSpecification(SpecificationEntity specificationToCreate) {
        return specificationRepository.save(specificationToCreate);
    }
}
