package com.Gr3ID12A.car_rental.domain.dto.rental;

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
public class RentalRequest {
    private UUID customerId;
    private UUID carId;
    private Date date_of_rental;
    private Date date_of_return;
    private UUID pick_up_placeId;
    private UUID return_placeId;
    private double total_cost;
    private String status;
}
