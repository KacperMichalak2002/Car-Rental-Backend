package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.entities.DiscountEntity;
import com.Gr3ID12A.car_rental.repositories.DiscountRepository;
import com.Gr3ID12A.car_rental.services.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;

    @Override
    public List<DiscountEntity> listDiscounts() {
        return discountRepository.findAll();
    }

    @Override
    public DiscountEntity createDiscount(DiscountEntity discountToCreate) {
        return discountRepository.save(discountToCreate);
    }

    @Override
    public Optional<DiscountEntity> getDiscount(UUID id) {
        return discountRepository.findById(id);
    }
}
