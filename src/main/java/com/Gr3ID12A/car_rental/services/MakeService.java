package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.make.MakeDto;
import com.Gr3ID12A.car_rental.domain.dto.make.MakeRequest;
import java.util.List;
import java.util.UUID;

public interface MakeService {
    List<MakeDto> listMakes();

    MakeDto createMake(MakeRequest makeRequest);

    void delete(UUID id);
}
