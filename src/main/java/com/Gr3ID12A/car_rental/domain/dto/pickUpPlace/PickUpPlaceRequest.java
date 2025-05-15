package com.Gr3ID12A.car_rental.domain.dto.pickUpPlace;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PickUpPlaceRequest {
    @NotNull
    private String name;
    @NotNull
    private UUID addressId;
}
