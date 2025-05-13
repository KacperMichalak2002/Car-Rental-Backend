package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.entities.DiscountEntity;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiscountService {
    List<DiscountEntity> listDiscounts();

    DiscountEntity createDiscount(DiscountEntity discountToCreate);

    Optional<DiscountEntity> getDiscount(UUID id);
}
