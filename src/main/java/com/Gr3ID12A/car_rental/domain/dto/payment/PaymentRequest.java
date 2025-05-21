package com.Gr3ID12A.car_rental.domain.dto.payment;

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
public class PaymentRequest {
    private UUID rentalId;
    private UUID paymentTypeId;
    private double cost;
    private Date dateOfPayment;
    private String status;
}
