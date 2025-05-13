package com.Gr3ID12A.car_rental.domain.dto.paymentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentTypeRequest {
    private String name;
}
