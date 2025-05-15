package com.Gr3ID12A.car_rental.services;


import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceRequest;

import java.util.List;

public interface ReturnPlaceService {
    List<ReturnPlaceDto> listReturnPlaces();

    ReturnPlaceDto createReturnPlace( ReturnPlaceRequest returnPlaceRequest);
}
