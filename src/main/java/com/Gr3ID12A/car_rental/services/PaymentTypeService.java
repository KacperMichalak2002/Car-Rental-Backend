package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeRequest;

import java.util.List;
import java.util.UUID;

public interface PaymentTypeService {
    List<PaymentTypeDto> listPaymentType();

    PaymentTypeDto createPaymentType(PaymentTypeRequest paymentTypeRequest);

    boolean isExist(UUID paymentTypeId);
}
