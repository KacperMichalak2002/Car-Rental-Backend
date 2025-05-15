package com.Gr3ID12A.car_rental.domain.dto.returnPlace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnPlaceRequest {
    private String name;
    private UUID addressId;
}
