package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeRequest;
import com.Gr3ID12A.car_rental.domain.entities.BodyTypeEntity;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface BodyTypeService {
    List<BodyTypeEntity> listBodyTypes();

    BodyTypeEntity createBodyType(BodyTypeEntity bodyTypeToCreate);

    void delete(UUID id);
}
