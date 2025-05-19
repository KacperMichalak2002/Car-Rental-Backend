package com.Gr3ID12A.car_rental.services;


import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.ReturnPlaceEntity;

import java.util.List;
import java.util.UUID;

public interface ReturnPlaceService {
    List<ReturnPlaceDto> listReturnPlaces();

    ReturnPlaceDto createReturnPlace( ReturnPlaceRequest returnPlaceRequest);

    ReturnPlaceEntity getReturnPlaceEntityById(UUID returnPlaceId);
}
