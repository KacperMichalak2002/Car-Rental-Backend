package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.model.ModelDto;
import com.Gr3ID12A.car_rental.domain.dto.model.ModelRequest;

import java.util.List;
import java.util.UUID;

public interface ModelService {
    List<ModelDto> listModels();

    ModelDto createModel(ModelRequest modelRequest);

    void delete(UUID id);
}
