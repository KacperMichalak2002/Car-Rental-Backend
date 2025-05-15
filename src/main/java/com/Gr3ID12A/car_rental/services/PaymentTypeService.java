package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeRequest;

import java.util.List;

public interface PaymentTypeService {
    List<PaymentTypeDto> listPaymentType();

    PaymentTypeDto createPaymentType(PaymentTypeRequest paymentTypeRequest);
}
