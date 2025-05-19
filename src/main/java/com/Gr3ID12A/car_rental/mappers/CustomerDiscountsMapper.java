package com.Gr3ID12A.car_rental.mappers;

import com.Gr3ID12A.car_rental.domain.dto.customerDiscounts.CustomerDiscountsDto;
import com.Gr3ID12A.car_rental.domain.dto.customerDiscounts.CustomerDiscountsRequest;
import com.Gr3ID12A.car_rental.domain.entities.CustomerDiscountsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerDiscountsMapper {

    CustomerDiscountsEntity toEntity(CustomerDiscountsRequest customerDiscountsRequest);
    CustomerDiscountsDto toDto(CustomerDiscountsEntity customerDiscountsEntity);
}
