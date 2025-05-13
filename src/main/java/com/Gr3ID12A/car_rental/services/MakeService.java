package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.make.MakeDto;
import com.Gr3ID12A.car_rental.domain.entities.MakeEntity;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface MakeService {
    List<MakeEntity> listMakes();

    MakeEntity createMake(MakeEntity makeToCreate);

    void delete(UUID id);
}
