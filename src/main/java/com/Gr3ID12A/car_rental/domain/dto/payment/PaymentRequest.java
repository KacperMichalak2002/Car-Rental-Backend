package com.Gr3ID12A.car_rental.domain.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private String title;
    private UUID rentalId;
    private UUID paymentTypeId;
    private BigDecimal cost;
    private LocalDate dateOfPayment;
    private String status;
}
