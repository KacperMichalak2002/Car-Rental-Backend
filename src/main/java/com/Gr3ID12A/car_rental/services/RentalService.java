package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalRequest;

import java.util.List;
import java.util.UUID;

public interface RentalService {
    List<RentalDto> listRentals();

    List<RentalDto> listRentalsByCustomer(UUID id);

    RentalDto createRental(RentalRequest rentalRequest);

    void delete(UUID id);

    boolean isExist(UUID rentalId);

    RentalDto partialUpdateRental(UUID id, RentalRequest rentalRequest);
}
