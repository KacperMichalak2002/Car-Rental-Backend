package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeRequest;
import com.Gr3ID12A.car_rental.domain.entities.PaymentTypeEntity;
import com.Gr3ID12A.car_rental.mappers.PaymentTypeMapper;
import com.Gr3ID12A.car_rental.repositories.PaymentTypeRepository;
import com.Gr3ID12A.car_rental.services.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {
    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentTypeMapper paymentTypeMapper;
    @Override
    public List<PaymentTypeDto> listPaymentType() {
        return paymentTypeRepository.findAll()
                .stream()
                .map(paymentTypeMapper::toDto)
                .toList();
    }

    @Override
    public PaymentTypeDto createPaymentType(PaymentTypeRequest paymentTypeRequest) {
        PaymentTypeEntity paymentTypeToCreate = paymentTypeMapper.toEntity(paymentTypeRequest);
        PaymentTypeEntity savedPaymentType = paymentTypeRepository.save(paymentTypeToCreate);
        return paymentTypeMapper.toDto(savedPaymentType);
    }
}
