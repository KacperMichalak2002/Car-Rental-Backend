package com.Gr3ID12A.car_rental.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {
    private UUID id;
    private RentalDto rental;
    private PaymentTypeDto payment_type;
    private double cost;
    private Date date_of_payment;
    private String status;
}
