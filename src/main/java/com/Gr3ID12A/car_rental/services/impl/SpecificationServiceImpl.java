package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationDto;
import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationRequest;
import com.Gr3ID12A.car_rental.domain.entities.SpecificationEntity;
import com.Gr3ID12A.car_rental.mappers.SpecificationMapper;
import com.Gr3ID12A.car_rental.repositories.SpecificationRepository;
import com.Gr3ID12A.car_rental.services.SpecificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecificationServiceImpl implements SpecificationService {
    private final SpecificationRepository specificationRepository;
    private final SpecificationMapper specificationMapper;

    @Override
    public List<SpecificationDto> listSpecifications() {
        return specificationRepository.findAll()
                .stream()
                .map(specificationMapper::toDto)
                .toList();
    }

    @Override
    public SpecificationDto getSpecification(UUID id) {
        Optional<SpecificationEntity> specificationFound = specificationRepository.findById(id);
        return specificationFound.map(specificationEntity -> {
            SpecificationDto specificationDto = specificationMapper.toDto(specificationEntity);
            return specificationDto;
        }).orElse(null);
    }

    @Override
    public SpecificationDto createSpecification(SpecificationRequest specificationRequest) {
        SpecificationEntity specificationToCreate = specificationMapper.toEntity(specificationRequest);
        SpecificationEntity specificationSaved = specificationRepository.save(specificationToCreate);
        return specificationMapper.toDto(specificationSaved);
    }

    @Override
    public boolean isExist(UUID specificationId) {
        return specificationRepository.existsById(specificationId);
    }

    @Override
    public SpecificationEntity getSpecificationEntityById(UUID specificationId) {
        return specificationRepository.findById(specificationId).orElseThrow(() -> new EntityNotFoundException("Specification not found"));
    }
}
