package com.Gr3ID12A.car_rental.domain.dto.payment;

import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;
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
public class PaymentDto {
    private UUID id;
    private String title;
    private String sessionId;
    private RentalDto rental;
    private PaymentTypeDto payment_type;
    private BigDecimal cost;
    private LocalDate date_of_payment;
    private String status;
}
