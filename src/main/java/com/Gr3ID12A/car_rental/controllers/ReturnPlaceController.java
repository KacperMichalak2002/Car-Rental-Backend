package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceRequest;
import com.Gr3ID12A.car_rental.services.AddressService;
import com.Gr3ID12A.car_rental.services.ReturnPlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/returnPlaces")
@RequiredArgsConstructor

public class ReturnPlaceController {
    private final ReturnPlaceService returnPlaceService;
    private final AddressService addressService;
    @GetMapping
    public ResponseEntity<List<ReturnPlaceDto>> listReturnPlaces(){
        List<ReturnPlaceDto> returnPlaces = returnPlaceService.listReturnPlaces();
        return new ResponseEntity<>(returnPlaces, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReturnPlaceDto> createReturnPlace(@Valid @RequestBody ReturnPlaceRequest returnPlaceRequest){
        boolean addressExist = addressService.isExist(returnPlaceRequest.getAddressId());
        if(!addressExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ReturnPlaceDto savedReturnPlace = returnPlaceService.createReturnPlace(returnPlaceRequest);
        return new ResponseEntity<>(savedReturnPlace, HttpStatus.CREATED);

    }
}
