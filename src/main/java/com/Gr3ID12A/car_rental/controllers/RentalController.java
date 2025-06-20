package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalRequest;
import com.Gr3ID12A.car_rental.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    private final CustomerService customerService;
    private final CarService carService;
    private final PickUpPlaceService pickUpPlaceService;
    private final ReturnPlaceService returnPlaceService;

    @GetMapping
    public ResponseEntity<List<RentalDto>> listRental(){
        List<RentalDto> rentals = rentalService.listRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    @GetMapping(path = "/customers/{customerId}")
    public ResponseEntity<List<RentalDto>> listRentalsByCustomer(@PathVariable("customerId")UUID id){
        List<RentalDto> rentalsByCustomer = rentalService.listRentalsByCustomer(id);
        return new ResponseEntity<>(rentalsByCustomer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RentalDto> createRental(@RequestBody RentalRequest rentalRequest){
        boolean customerExist = customerService.isExist(rentalRequest.getCustomerId());
        boolean carExist = carService.isExist(rentalRequest.getCarId());
        boolean pickUpPlaceExist = pickUpPlaceService.isExist(rentalRequest.getPick_up_placeId());
        boolean returnPlaceExist = returnPlaceService.isExist(rentalRequest.getReturn_placeId());

        if(!customerExist || !carExist || !pickUpPlaceExist || !returnPlaceExist) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RentalDto createdRental = rentalService.createRental(rentalRequest);
        return new ResponseEntity<>(createdRental, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable("id")UUID id){
        rentalService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/{id}/status")
    public ResponseEntity<RentalDto> partialUpdateRental(@PathVariable("id") UUID id, @RequestBody RentalRequest rentalRequest){
        boolean rentalExist = rentalService.isExist(id);

        if(!rentalExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        RentalDto updatedRental = rentalService.partialUpdateRental(id, rentalRequest);
        return new ResponseEntity<>(updatedRental, HttpStatus.OK);

    }






}
