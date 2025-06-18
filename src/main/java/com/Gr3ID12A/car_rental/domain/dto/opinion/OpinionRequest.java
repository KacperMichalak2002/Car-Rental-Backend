package com.Gr3ID12A.car_rental.domain.dto.opinion;

import jakarta.validation.constraints.NotNull;
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
public class OpinionRequest {

    @NotNull
    private UUID customerId;

    @NotNull
    private UUID carId;

    @NotNull
    private int rating;

    private String description;

    private LocalDate date_of_publishing;
}
