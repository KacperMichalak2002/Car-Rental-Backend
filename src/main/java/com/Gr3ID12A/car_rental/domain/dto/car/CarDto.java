package com.Gr3ID12A.car_rental.domain.dto.car;

import com.Gr3ID12A.car_rental.domain.dto.ModelDto;
import com.Gr3ID12A.car_rental.domain.dto.SpecificationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDto {
    private UUID id;
    private ModelDto model;
    private SpecificationDto specification;
    private double cost;
    private double deposit;
    private String availability;
    private String image_url;
    private String description;
}
