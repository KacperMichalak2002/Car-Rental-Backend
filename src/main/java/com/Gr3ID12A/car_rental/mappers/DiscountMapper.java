package com.Gr3ID12A.car_rental.mappers;

import com.Gr3ID12A.car_rental.domain.dto.discount.DiscountDto;
import com.Gr3ID12A.car_rental.domain.dto.discount.DiscountRequest;
import com.Gr3ID12A.car_rental.domain.entities.CustomerDiscountsEntity;
import com.Gr3ID12A.car_rental.domain.entities.DiscountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiscountMapper {

    DiscountDto toDto(DiscountEntity discountEntity);
    DiscountEntity toEntity(DiscountRequest discountRequest);

    default Set<CustomerDiscountsEntity> mapCustomerDiscounts(Set<UUID> customerDiscountIds) {
        if (customerDiscountIds == null) {
            return null;
        }
        return customerDiscountIds.stream()
                .map(id -> {
                    CustomerDiscountsEntity entity = new CustomerDiscountsEntity();
                    entity.setId(id);
                    return entity;
                })
                .collect(Collectors.toSet());
    }
}
