package com.Gr3ID12A.car_rental.domain.dto.car;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarRequest {

    @NotNull
    private UUID modelId;

    @NotNull
    private UUID specificationId;

    @NotNull
    private BigDecimal cost;

    @NotNull
    private BigDecimal deposit;

    private String availability;

    private String image_url;

    private String description;
}
