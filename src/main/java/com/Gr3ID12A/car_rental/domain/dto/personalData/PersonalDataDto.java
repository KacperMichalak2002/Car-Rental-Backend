package com.Gr3ID12A.car_rental.domain.dto.personalData;

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
public class PersonalDataDto {
    private UUID id;
    private AddressDto address;
    private String first_name;
    private String last_name;
    private String pesel;
    private String id_number;
    private String phone_number;
    private String email;
}
