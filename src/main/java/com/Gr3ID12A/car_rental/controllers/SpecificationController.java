package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationDto;
import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationRequest;
import com.Gr3ID12A.car_rental.services.SpecificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/specifications")
@RequiredArgsConstructor

public class SpecificationController {
    private final SpecificationService specificationService;

    @GetMapping
    public ResponseEntity<List<SpecificationDto>> listSpecifications() {
        List<SpecificationDto> specifications = specificationService.listSpecifications();
        return ResponseEntity.ok(specifications);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SpecificationDto> getSpecification(@PathVariable("id")UUID id){
        SpecificationDto specification = specificationService.getSpecification(id);
        if(specification == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(specification,HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<SpecificationDto> createSpecification(@Valid @RequestBody SpecificationRequest specificationRequest){
        SpecificationDto savedSpecification = specificationService.createSpecification(specificationRequest);
        return new ResponseEntity<>(savedSpecification, HttpStatus.CREATED);
    }
}
