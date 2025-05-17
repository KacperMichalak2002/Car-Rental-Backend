package com.Gr3ID12A.car_rental.mappers;

import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.ReturnPlaceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReturnPlaceMapper {
    ReturnPlaceDto toDto(ReturnPlaceEntity returnPlaceEntity);

    ReturnPlaceEntity toEntity(ReturnPlaceRequest returnPlaceRequest);
}
