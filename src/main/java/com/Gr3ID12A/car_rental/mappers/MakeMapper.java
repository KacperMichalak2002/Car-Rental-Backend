package com.Gr3ID12A.car_rental.mappers;

import com.Gr3ID12A.car_rental.domain.dto.make.MakeDto;
import com.Gr3ID12A.car_rental.domain.dto.make.MakeRequest;
import com.Gr3ID12A.car_rental.domain.entities.MakeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MakeMapper {

    MakeDto toDto(MakeEntity makeEntity);
    MakeEntity toEntity(MakeRequest makeRequest);
}
