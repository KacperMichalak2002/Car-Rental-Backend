package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.entities.PaymentTypeEntity;

import java.util.List;

public interface PaymentTypeService {
    List<PaymentTypeEntity> listPaymentType();

    PaymentTypeEntity createPaymentType(PaymentTypeEntity paymentTypeToCreate);
}
