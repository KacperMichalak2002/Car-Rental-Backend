package com.Gr3ID12A.car_rental.domain.dto.insurance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceRequest {
    private UUID rentalId;
    private String insurance_type;
    private double cost;
    private String range_of_insurance;
}
