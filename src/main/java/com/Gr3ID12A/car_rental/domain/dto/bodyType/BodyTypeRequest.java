package com.Gr3ID12A.car_rental.domain.dto.bodyType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BodyTypeRequest {
    private String name;
}
