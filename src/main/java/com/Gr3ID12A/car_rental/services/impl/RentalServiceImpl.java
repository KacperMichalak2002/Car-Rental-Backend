package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalRequest;
import com.Gr3ID12A.car_rental.domain.entities.*;
import com.Gr3ID12A.car_rental.mappers.RentalMapper;
import com.Gr3ID12A.car_rental.repositories.RentalRepository;
import com.Gr3ID12A.car_rental.services.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        CustomerEntity customer = new CustomerEntity();
        customer.setId(rentalRequest.getCustomerId());

        CarEntity car = new CarEntity();
        car.setId(rentalRequest.getCarId());

        PickUpPlaceEntity pickUpPlace = new PickUpPlaceEntity();
        pickUpPlace.setId(rentalRequest.getPick_up_placeId());

        ReturnPlaceEntity returnPlace = new ReturnPlaceEntity();
        returnPlace.setId(rentalRequest.getReturn_placeId());

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

    @Override
    public boolean isExist(UUID rentalId) {
        return rentalRepository.existsById(rentalId);
    }

    @Override
    public RentalDto partialUpdateRental(UUID id, RentalRequest rentalRequest) {
        RentalEntity rentalToUpdate = rentalMapper.toEntity(rentalRequest);

        RentalEntity updatedRental = rentalRepository.findById(id).map(existingRental -> {
            Optional.ofNullable(rentalToUpdate.getStatus()).ifPresent(existingRental::setStatus);
            return rentalRepository.save(existingRental);
        }).orElseThrow( () -> new EntityNotFoundException("Rental was not found"));

        return rentalMapper.toDto(updatedRental);
    }
}
