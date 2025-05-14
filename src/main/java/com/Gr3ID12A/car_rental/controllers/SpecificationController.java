package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationDto;
import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationRequest;
import com.Gr3ID12A.car_rental.domain.entities.SpecificationEntity;
import com.Gr3ID12A.car_rental.mappers.SpecificationMapper;
import com.Gr3ID12A.car_rental.services.SpecificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/specifications")
@RequiredArgsConstructor

public class SpecificationController {
    private final SpecificationService specificationService;
    private final SpecificationMapper specificationMapper;

    @GetMapping
    public ResponseEntity<List<SpecificationDto>> listSpecifications() {
        List<SpecificationDto> specifications = specificationService.listSpecifications()
                .stream()
                .map(specificationMapper::toDto)
                .toList();
        return ResponseEntity.ok(specifications);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SpecificationDto> getSpecification(@PathVariable("id")UUID id){
        Optional<SpecificationEntity> specification = specificationService.getSpecification(id);
        return specification.map(specificationEntity -> {
            SpecificationDto specificationDto = specificationMapper.toDto(specificationEntity);
            return new ResponseEntity<>(specificationDto, HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping
    public ResponseEntity<SpecificationDto> createSpecification(@Valid @RequestBody SpecificationRequest specificationRequest){
        SpecificationEntity specificationToCreate = specificationMapper.toEntity(specificationRequest);
        SpecificationEntity savedSpecification = specificationService.createSpecification(specificationToCreate);
        return new ResponseEntity<>(specificationMapper.toDto(savedSpecification), HttpStatus.CREATED);
    }
}
