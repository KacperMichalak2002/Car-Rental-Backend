package com.Gr3ID12A.car_rental.domain.dto;

import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.make.MakeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModelDto {
    private UUID id;
    private MakeDto make;
    private BodyTypeDto bodyType;
    private String name;
}
