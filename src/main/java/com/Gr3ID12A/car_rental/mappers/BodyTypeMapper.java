package com.Gr3ID12A.car_rental.mappers;

import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeRequest;
import com.Gr3ID12A.car_rental.domain.entities.BodyTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BodyTypeMapper {

    BodyTypeDto toDto(BodyTypeEntity bodyTypeEntity);
    BodyTypeEntity toEntity(BodyTypeRequest bodyTypeRequest);
}
