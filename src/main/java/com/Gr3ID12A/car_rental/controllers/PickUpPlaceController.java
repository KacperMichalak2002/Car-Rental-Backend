package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.PickUpPlaceEntity;
import com.Gr3ID12A.car_rental.mappers.PickUpPlaceMapper;
import com.Gr3ID12A.car_rental.services.AddressService;
import com.Gr3ID12A.car_rental.services.PickUpPlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/pickUpPlaces")
@RequiredArgsConstructor
public class PickUpPlaceController {

    private final PickUpPlaceService pickUpPlaceService;
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<PickUpPlaceDto>> listPickUpPlaces(){
        List<PickUpPlaceDto> pickUpPlaces =  pickUpPlaceService.listPickUpPlaces();
        return ResponseEntity.ok(pickUpPlaces);
    }

    @PostMapping
    public ResponseEntity<PickUpPlaceDto> createPickUpPlace(@Valid @RequestBody PickUpPlaceRequest pickUpPlaceRequest){
        boolean addressExist = addressService.isExist(pickUpPlaceRequest.getAddressId());
        if(!addressExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PickUpPlaceDto pickUpPlaceSaved = pickUpPlaceService.createPickUpPlace(pickUpPlaceRequest);
        return new ResponseEntity<>(pickUpPlaceSaved, HttpStatus.CREATED);
    }
}
