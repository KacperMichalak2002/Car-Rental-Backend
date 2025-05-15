package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceRequest;


import java.util.List;

public interface PickUpPlaceService {
    List<PickUpPlaceDto> listPickUpPlaces();

    PickUpPlaceDto createPickUpPlace(PickUpPlaceRequest pickUpPlaceRequest);
}
