package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalRequest;
import com.Gr3ID12A.car_rental.domain.entities.*;
import com.Gr3ID12A.car_rental.mappers.RentalMapper;
import com.Gr3ID12A.car_rental.repositories.RentalRepository;
import com.Gr3ID12A.car_rental.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {
    private final RentalMapper rentalMapper;
    private final RentalRepository rentalRepository;
    private final CustomerService customerService;
    private final CarService carService;
    private final PickUpPlaceService pickUpPlaceService;
    private final ReturnPlaceService returnPlaceService;


    @Override
    public List<RentalDto> listRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    @Override
    public List<RentalDto> listRentalsByCustomer(UUID id) {
        return rentalRepository.findAllByCustomer_Id(id)
                .stream()
                .map(rentalMapper::toDto)
                .toList();
    }

    @Override
    public RentalDto createRental(RentalRequest rentalRequest) {
        CustomerEntity customer = customerService.getCustomerEntityById(rentalRequest.getCustomerId());
        CarEntity car = carService.getCarEntityById(rentalRequest.getCarId());
        PickUpPlaceEntity pickUpPlace = pickUpPlaceService.getPickUpPlaceById(rentalRequest.getPick_up_placeId());
        ReturnPlaceEntity returnPlace = returnPlaceService.getReturnPlaceEntityById(rentalRequest.getReturn_placeId());

        RentalEntity rentalToCreate = rentalMapper.toEntity(rentalRequest);

        rentalToCreate.setCustomer(customer);
        rentalToCreate.setCar(car);
        rentalToCreate.setPick_up_place(pickUpPlace);
        rentalToCreate.setReturn_place(returnPlace);

        RentalEntity savedRental = rentalRepository.save(rentalToCreate);

        return rentalMapper.toDto(savedRental);
    }

    @Override
    public void delete(UUID id) {
        rentalRepository.deleteById(id);
    }
}
