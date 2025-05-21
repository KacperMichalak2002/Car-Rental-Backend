package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.PickUpPlaceEntity;


import java.util.List;
import java.util.UUID;

public interface PickUpPlaceService {
    List<PickUpPlaceDto> listPickUpPlaces();

    PickUpPlaceDto createPickUpPlace(PickUpPlaceRequest pickUpPlaceRequest);

    PickUpPlaceEntity getPickUpPlaceById(UUID pickUpPlaceId);

    boolean isExist(UUID pickUpPlaceId);
}
