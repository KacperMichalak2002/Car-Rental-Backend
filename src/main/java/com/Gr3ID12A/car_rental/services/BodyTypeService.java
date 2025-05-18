package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeRequest;
import com.Gr3ID12A.car_rental.domain.entities.BodyTypeEntity;

import java.util.List;
import java.util.UUID;

public interface BodyTypeService {
    List<BodyTypeDto> listBodyTypes();

    BodyTypeDto createBodyType(BodyTypeRequest bodyTypeRequest);

    void delete(UUID id);

    boolean isExist(UUID bodyTypeId);

    BodyTypeEntity getBodyTypeById(UUID bodyTypeId);
}
