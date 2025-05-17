package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataDto;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataRequest;
import com.Gr3ID12A.car_rental.services.AddressService;
import com.Gr3ID12A.car_rental.services.PersonalDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/personalData")
@RequiredArgsConstructor
public class PersonalDataController {
    private final PersonalDataService personalDataService;
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<PersonalDataDto>> listPersonalData(){
        List<PersonalDataDto> personalDataDtos = personalDataService.listPersonalData();
        return new ResponseEntity<>(personalDataDtos, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PersonalDataDto> getPersonalData(@PathVariable("id") UUID id){
        PersonalDataDto personalDataDto = personalDataService.getPersonalData(id);
        if(personalDataDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(personalDataDto,HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<PersonalDataDto> createPersonalData(@Valid @RequestBody PersonalDataRequest personalDataRequest){
        boolean addressExist = addressService.isExist(personalDataRequest.getAddressId());
        if(!addressExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PersonalDataDto savedPersonalData = personalDataService.createPersonalData(personalDataRequest);
        return new ResponseEntity<>(savedPersonalData, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<PersonalDataDto> partialUpdatePersonalData(@PathVariable("id") UUID id, @RequestBody PersonalDataRequest personalDataRequest){
        boolean personalDataExist = personalDataService.isExist(id);
        if(!personalDataExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PersonalDataDto updatedPersonalData = personalDataService.partialUpdate(id, personalDataRequest);
        return new ResponseEntity<>(updatedPersonalData, HttpStatus.OK);

    }
}
