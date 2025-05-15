package com.Gr3ID12A.car_rental.domain.dto.pickUpPlace;

import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PickUpPlaceDto {
    private UUID id;
    private String name;
    private AddressEntity address;
}
