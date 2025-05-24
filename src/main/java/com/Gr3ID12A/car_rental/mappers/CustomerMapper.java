package com.Gr3ID12A.car_rental.mappers;

import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerDto;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerRequest;
import com.Gr3ID12A.car_rental.domain.entities.CustomerEntity;
import com.Gr3ID12A.car_rental.domain.entities.DiscountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    @Mapping(target = "discountsIds", source = "discounts")
    CustomerDto toDto(CustomerEntity customerEntity);

    default Set<UUID> mapToUUID(Set<DiscountEntity> value){
        if(value == null){
            return Collections.emptySet();
        }

        return value.stream().map(DiscountEntity::getId).collect(Collectors.toSet());
    }

    CustomerEntity toEntity(CustomerRequest customerRequest);
}
