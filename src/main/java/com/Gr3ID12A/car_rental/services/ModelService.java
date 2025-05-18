package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.model.ModelDto;
import com.Gr3ID12A.car_rental.domain.dto.model.ModelRequest;
import com.Gr3ID12A.car_rental.domain.entities.ModelEntity;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public interface ModelService {
    List<ModelDto> listModels();

    ModelDto createModel(ModelRequest modelRequest);

    void delete(UUID id);

    boolean isExist(@NotNull UUID modelId);

    ModelEntity getModelEntityById(@NotNull UUID specificationId);
}
