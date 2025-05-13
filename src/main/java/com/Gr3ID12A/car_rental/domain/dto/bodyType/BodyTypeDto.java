package com.Gr3ID12A.car_rental.domain.dto.bodyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BodyTypeDto {
    private UUID id;
    private String name;
}
