package com.Gr3ID12A.car_rental.controllers;


import com.Gr3ID12A.car_rental.domain.dto.insurance.InsuranceDto;
import com.Gr3ID12A.car_rental.domain.dto.insurance.InsuranceRequest;
import com.Gr3ID12A.car_rental.services.InsuranceService;
import com.Gr3ID12A.car_rental.services.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/insurances")
@RequiredArgsConstructor
public class InsuranceController {

    private final InsuranceService insuranceService;
    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<InsuranceDto>> listInsurances(){
        List<InsuranceDto> insurances = insuranceService.listInsurances();
        return new ResponseEntity<>(insurances, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}/rental")
    public ResponseEntity<InsuranceDto> getInsuranceByRentalId(@PathVariable("id")UUID id){
        boolean isExist = rentalService.isExist(id);
        if(!isExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        InsuranceDto foundInsurance = insuranceService.getInsuranceByRentalId(id);

        return new ResponseEntity<>(foundInsurance, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InsuranceDto> createInsurance(@RequestBody InsuranceRequest insuranceRequest){
        boolean isRentalExist = rentalService.isExist(insuranceRequest.getRentalId());

        if(!isRentalExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        InsuranceDto insuranceDto = insuranceService.createInsurance(insuranceRequest);
        return new ResponseEntity<>(insuranceDto, HttpStatus.CREATED);
    }

}
