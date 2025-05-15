package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeRequest;
import com.Gr3ID12A.car_rental.domain.entities.BodyTypeEntity;
import com.Gr3ID12A.car_rental.mappers.BodyTypeMapper;
import com.Gr3ID12A.car_rental.repositories.BodyTypeRepository;
import com.Gr3ID12A.car_rental.services.BodyTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BodyTypeServiceImpl implements BodyTypeService {

    private final BodyTypeRepository bodyTypeRepository;
    private final BodyTypeMapper bodyTypeMapper;
    @Override
    public List<BodyTypeDto> listBodyTypes() {
        return bodyTypeRepository.findAll()
                .stream()
                .map(bodyTypeMapper::toDto)
                .toList();
    }

    @Override
    public BodyTypeDto createBodyType(BodyTypeRequest bodyTypeRequest) {
        BodyTypeEntity bodyTypeToCreate = bodyTypeMapper.toEntity(bodyTypeRequest);
        BodyTypeEntity savedBodyType = bodyTypeRepository.save(bodyTypeToCreate);
        return bodyTypeMapper.toDto(savedBodyType);
    }

    @Override
    public void delete(UUID id) {
        bodyTypeRepository.deleteById(id);
    }
}
