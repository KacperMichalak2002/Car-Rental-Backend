package com.Gr3ID12A.car_rental.domain.dto.returnPlace;

import com.Gr3ID12A.car_rental.domain.dto.address.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnPlaceDto {
    private UUID id;
    private String name;
    private AddressDto address;
}
