package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.entities.BodyTypeEntity;
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

    @Override
    public List<BodyTypeEntity> listBodyTypes() {
        return bodyTypeRepository.findAll();
    }

    @Override
    public BodyTypeEntity createBodyType(BodyTypeEntity bodyTypeToCreate) {
        return bodyTypeRepository.save(bodyTypeToCreate);
    }

    @Override
    public void delete(UUID id) {
        bodyTypeRepository.deleteById(id);
    }
}
