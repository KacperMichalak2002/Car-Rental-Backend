package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.address.AddressDto;
import com.Gr3ID12A.car_rental.domain.dto.address.AddressRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.mappers.AddressMapper;
import com.Gr3ID12A.car_rental.services.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @GetMapping
    public ResponseEntity<List<AddressDto>> listAddresses(){
        List<AddressDto> addresses = addressService.listAddresses()
                .stream()
                .map(addressMapper::toDto)
                .toList();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AddressDto> getAddress(@PathVariable("id")UUID id){
        Optional<AddressEntity> address = addressService.getAddress(id);
        return address.map(addressEntity -> {
            AddressDto addressDto = addressMapper.toDto(addressEntity);
            return new ResponseEntity<>(addressDto,HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@Valid @RequestBody AddressRequest addressRequest){
        AddressEntity addressToCreate = addressMapper.toEntity(addressRequest);
        AddressEntity savedAddress = addressService.createAddress(addressToCreate);
        return new ResponseEntity<>(addressMapper.toDto(savedAddress), HttpStatus.CREATED);
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
        AddressEntity addressToUpdate = addressMapper.toEntity(addressRequest);
        AddressEntity updatedAddress = addressService.partialUpdate(id ,addressToUpdate);
        return new ResponseEntity<>(addressMapper.toDto(updatedAddress), HttpStatus.OK);
    }



}
