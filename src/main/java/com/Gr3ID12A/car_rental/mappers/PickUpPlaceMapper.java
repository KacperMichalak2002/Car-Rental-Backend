package com.Gr3ID12A.car_rental.mappers;

import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.PickUpPlaceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PickUpPlaceMapper {

    PickUpPlaceDto toDto(PickUpPlaceEntity pickUpPlaceEntity);
    PickUpPlaceEntity toEntity(PickUpPlaceRequest pickUpPlaceRequest);
}
