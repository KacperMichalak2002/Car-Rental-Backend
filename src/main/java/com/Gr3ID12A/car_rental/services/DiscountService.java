package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.discount.DiscountDto;
import com.Gr3ID12A.car_rental.domain.dto.discount.DiscountRequest;

import java.util.List;
import java.util.UUID;

public interface DiscountService {
    List<DiscountDto> listDiscounts();

    DiscountDto createDiscount(DiscountRequest discountRequest);

    DiscountDto getDiscount(UUID id);
}
