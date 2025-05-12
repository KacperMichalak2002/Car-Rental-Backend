package com.Gr3ID12A.car_rental.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDiscountsDto {
    private UUID id;
    private CustomerDto customer;
    private DiscountDto discount;
    private String status;
}
