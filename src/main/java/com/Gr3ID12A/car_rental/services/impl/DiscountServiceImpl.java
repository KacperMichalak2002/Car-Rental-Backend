package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.discount.DiscountDto;
import com.Gr3ID12A.car_rental.domain.dto.discount.DiscountRequest;
import com.Gr3ID12A.car_rental.domain.entities.DiscountEntity;
import com.Gr3ID12A.car_rental.mappers.DiscountMapper;
import com.Gr3ID12A.car_rental.repositories.DiscountRepository;
import com.Gr3ID12A.car_rental.services.DiscountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountMapper discountMapper;
    @Override
    public List<DiscountDto> listDiscounts() {

        return discountRepository.findAll()
                .stream()
                .map(discountMapper::toDto)
                .toList();
    }

    @Override
    public DiscountDto createDiscount(DiscountRequest discountRequest) {
        DiscountEntity discountToCreate = discountMapper.toEntity(discountRequest);
        DiscountEntity savedDiscount = discountRepository.save(discountToCreate);
        return discountMapper.toDto(savedDiscount);
    }

    @Override
    public DiscountDto getDiscount(UUID id) {
        Optional<DiscountEntity> foundDiscount = discountRepository.findById(id);
        return foundDiscount.map(discountEntity -> {
            DiscountDto discountDto = discountMapper.toDto(discountEntity);
            return discountDto;
        }).orElse(null);
    }

    @Override
    public DiscountEntity getDiscountEntityById(UUID discountId) {
        return discountRepository.findById(discountId).orElseThrow(() -> new EntityNotFoundException("Discount not found"));
    }

    @Override
    public boolean isExist(UUID discountId) {
        return discountRepository.existsById(discountId);
    }
    
}
