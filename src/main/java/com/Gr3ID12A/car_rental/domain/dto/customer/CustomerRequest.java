package com.Gr3ID12A.car_rental.domain.dto.customer;

import jakarta.validation.constraints.NotNull;
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
public class CustomerRequest {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID personalDataId;

    private Set<UUID> discountsIds;

    private Date date_of_joining;
    private int loyalty_points;
}
