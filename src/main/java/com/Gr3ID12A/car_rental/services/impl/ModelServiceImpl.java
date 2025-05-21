package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.model.ModelDto;
import com.Gr3ID12A.car_rental.domain.dto.model.ModelRequest;
import com.Gr3ID12A.car_rental.domain.entities.BodyTypeEntity;
import com.Gr3ID12A.car_rental.domain.entities.MakeEntity;
import com.Gr3ID12A.car_rental.domain.entities.ModelEntity;
import com.Gr3ID12A.car_rental.mappers.ModelMapper;
import com.Gr3ID12A.car_rental.repositories.ModelRepository;
import com.Gr3ID12A.car_rental.services.ModelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<ModelDto> listModels() {
        return modelRepository.findAll()
                .stream()
                .map(modelMapper::toDto)
                .toList();
    }
    @Override
    public ModelDto createModel(ModelRequest modelRequest) {
        ModelEntity modelToCreate = modelMapper.toEntity(modelRequest);

        MakeEntity makeEntity = new MakeEntity();
        makeEntity.setId(modelRequest.getMakeId());

        BodyTypeEntity bodyTypeEntity = new BodyTypeEntity();
        bodyTypeEntity.setId(modelRequest.getBodyTypeId());

        modelToCreate.setMake(makeEntity);
        modelToCreate.setBodyType(bodyTypeEntity);

        ModelEntity modelSaved = modelRepository.save(modelToCreate);
        return modelMapper.toDto(modelSaved);
    }

    @Override
    public void delete(UUID id) {
        modelRepository.deleteById(id);
    }

    @Override
    public boolean isExist(UUID modelId) {
        return modelRepository.existsById(modelId);
    }

    @Override
    public ModelEntity getModelEntityById(UUID modelId) {
        return modelRepository.findById(modelId).orElseThrow(() -> new EntityNotFoundException("Model not found"));
    }
}
