package com.Gr3ID12A.car_rental.domain.dto.customerDiscounts;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDiscountsRequest {
    private UUID customerId;
    private UUID discountId;
    private String status;
}
