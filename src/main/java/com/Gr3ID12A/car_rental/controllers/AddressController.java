package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.address.AddressDto;
import com.Gr3ID12A.car_rental.domain.dto.address.AddressRequest;
import com.Gr3ID12A.car_rental.services.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressDto>> listAddresses(){
        List<AddressDto> addresses = addressService.listAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable("id")UUID id){
        AddressDto address = addressService.getAddress(id);
        if(address == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(address,HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@Valid @RequestBody AddressRequest addressRequest){
        AddressDto savedAddress = addressService.createAddress(addressRequest);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<AddressDto> partialUpdateAddress(
            @PathVariable("id") UUID id,
            @RequestBody AddressRequest addressRequest
    ){
        boolean addressExists = addressService.isExist(id);
        if(!addressExists){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AddressDto updatedAddress = addressService.partialUpdate(id ,addressRequest);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }



}
