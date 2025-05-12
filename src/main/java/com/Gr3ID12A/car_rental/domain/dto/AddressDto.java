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
public class AddressDto {
    private UUID id;
    private String country;
    private String postal_code;
    private String city;
    private String street;
    private String street_number;

}
