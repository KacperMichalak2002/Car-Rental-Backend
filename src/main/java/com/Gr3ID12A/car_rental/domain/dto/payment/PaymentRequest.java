package com.Gr3ID12A.car_rental.domain.dto.payment;

import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private String title;
    private UUID rentalId;
    private BigDecimal cost;
    private PaymentName paymentType;
}
