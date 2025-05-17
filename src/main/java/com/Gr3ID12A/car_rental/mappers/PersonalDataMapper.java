package com.Gr3ID12A.car_rental.mappers;

import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataDto;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataRequest;
import com.Gr3ID12A.car_rental.domain.entities.PersonalDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonalDataMapper {
    PersonalDataDto toDto(PersonalDataEntity personalDataEntity);
    PersonalDataEntity toEntity(PersonalDataRequest personalDataRequest);
}
