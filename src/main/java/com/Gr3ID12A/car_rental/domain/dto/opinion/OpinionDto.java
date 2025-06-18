package com.Gr3ID12A.car_rental.domain.dto.opinion;

import com.Gr3ID12A.car_rental.domain.dto.car.CarDto;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpinionDto {
    private UUID id;
    private CustomerDto customer;
    private CarDto car;
    private int rating;
    private String description;
    private LocalDate date_of_publishing;
}
