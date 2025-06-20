package com.Gr3ID12A.car_rental.domain.dto.insurance;

import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceDto {
    private UUID id;
    private RentalDto rental;
    private String insurance_type;
    private double cost;
    private String range_of_insurance;
}
