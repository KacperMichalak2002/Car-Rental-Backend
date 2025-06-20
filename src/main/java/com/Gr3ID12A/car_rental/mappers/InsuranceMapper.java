package com.Gr3ID12A.car_rental.mappers;

import com.Gr3ID12A.car_rental.domain.dto.insurance.InsuranceDto;
import com.Gr3ID12A.car_rental.domain.dto.insurance.InsuranceRequest;
import com.Gr3ID12A.car_rental.domain.entities.InsuranceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InsuranceMapper {

    InsuranceDto toDto (InsuranceEntity insuranceEntity);

    InsuranceEntity toEntity (InsuranceRequest insuranceRequest);
}
