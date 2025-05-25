package com.Gr3ID12A.car_rental.domain.dto.customer;

import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataDto;
import com.Gr3ID12A.car_rental.domain.dto.user.UserDto;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private UUID id;
    private UserDto user;
    private PersonalDataDto personalData;
    private Set<UUID> discountsIds;
    private Date date_of_joining;
    private int loyalty_points;
}
