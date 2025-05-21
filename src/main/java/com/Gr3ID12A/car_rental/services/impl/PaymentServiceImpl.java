package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentDto;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentRequest;
import com.Gr3ID12A.car_rental.domain.entities.PaymentEntity;
import com.Gr3ID12A.car_rental.domain.entities.PaymentTypeEntity;
import com.Gr3ID12A.car_rental.domain.entities.RentalEntity;
import com.Gr3ID12A.car_rental.mappers.PaymentMapper;
import com.Gr3ID12A.car_rental.repositories.PaymentRepository;
import com.Gr3ID12A.car_rental.services.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;

    @Override
    public List<PaymentDto> listPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    @Override
    public PaymentDto createPayment(PaymentRequest paymentRequest) {
        PaymentEntity paymentToSave = paymentMapper.toEntity(paymentRequest);

        RentalEntity rental = new RentalEntity();
        rental.setId(paymentRequest.getRentalId());
        paymentToSave.setRental(rental);

        PaymentTypeEntity paymentType = new PaymentTypeEntity();
        paymentType.setId(paymentRequest.getPaymentTypeId());
        paymentToSave.setPayment_type(paymentType);

        PaymentEntity savedPayment = paymentRepository.save(paymentToSave);

        return paymentMapper.toDto(savedPayment);

    }

    @Override
    public boolean isExist(UUID id) {
        return paymentRepository.existsById(id);
    }

    @Override
    public PaymentDto getPaymentById(UUID id) {
        Optional<PaymentEntity> payment = paymentRepository.findById(id);
        return payment.map(paymentEntity -> {
            PaymentDto paymentDto = paymentMapper.toDto(paymentEntity);
            return paymentDto;
        }).orElse(null);
    }

    @Override
    public PaymentDto partialUpdatePayment(UUID id, PaymentRequest paymentRequest) {
        PaymentEntity paymentToUpdate = paymentMapper.toEntity(paymentRequest);

        PaymentEntity updatedPayment = paymentRepository.findById(id).map(existingPayment ->{
            Optional.ofNullable(paymentToUpdate.getStatus()).ifPresent(existingPayment::setStatus);
            return paymentRepository.save(existingPayment);
        }).orElseThrow(() -> new EntityNotFoundException("Payment now found"));

        return paymentMapper.toDto(updatedPayment);
    }
}
