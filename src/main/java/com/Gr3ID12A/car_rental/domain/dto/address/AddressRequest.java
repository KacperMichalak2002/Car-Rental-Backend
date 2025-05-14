package com.Gr3ID12A.car_rental.domain.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequest {
    private String country;
    private String postal_code;
    private String city;
    private String street;
    private String street_number;
}
