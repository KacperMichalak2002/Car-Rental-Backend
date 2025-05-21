package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentDto;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentRequest;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    List<PaymentDto> listPayments();

    PaymentDto createPayment(PaymentRequest paymentRequest);

    boolean isExist(UUID id);

    PaymentDto getPaymentById(UUID id);

    PaymentDto partialUpdatePayment(UUID id, PaymentRequest paymentRequest);
}
