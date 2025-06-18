package com.Gr3ID12A.car_rental.mappers;

import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeRequest;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentTypeMapper {
    PaymentTypeDto toDto(PaymentTypeEntity paymentTypeEntity);
    PaymentTypeEntity toEntity(PaymentTypeRequest paymentTypeRequest);
}
