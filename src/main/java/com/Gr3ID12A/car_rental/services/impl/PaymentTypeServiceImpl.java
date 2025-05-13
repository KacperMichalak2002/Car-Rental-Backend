package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.entities.PaymentTypeEntity;
import com.Gr3ID12A.car_rental.repositories.PaymentTypeRepository;
import com.Gr3ID12A.car_rental.services.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {
    private final PaymentTypeRepository paymentTypeRepository;

    @Override
    public List<PaymentTypeEntity> listPaymentType() {
        return paymentTypeRepository.findAll();
    }

    @Override
    public PaymentTypeEntity createPaymentType(PaymentTypeEntity paymentTypeToCreate) {
        return paymentTypeRepository.save(paymentTypeToCreate);
    }
}
