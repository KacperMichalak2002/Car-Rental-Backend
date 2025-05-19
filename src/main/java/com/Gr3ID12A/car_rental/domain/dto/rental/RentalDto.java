package com.Gr3ID12A.car_rental.domain.dto.rental;

import com.Gr3ID12A.car_rental.domain.dto.car.CarDto;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerDto;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalDto {
    private UUID id;
    private CustomerDto customer;
    private CarDto car;
    private Date date_of_rental;
    private Date date_of_return;
    private PickUpPlaceDto pick_up_place;
    private ReturnPlaceDto return_place;
    private double total_cost;
    private String status;
}
