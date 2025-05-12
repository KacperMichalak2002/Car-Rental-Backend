package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.car.CarDto;
import com.Gr3ID12A.car_rental.domain.dto.car.CarRequest;
import com.Gr3ID12A.car_rental.domain.entities.CarEntity;
import com.Gr3ID12A.car_rental.mappers.CarMapper;
import com.Gr3ID12A.car_rental.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final CarMapper carMapper;

    @GetMapping
    public ResponseEntity<List<CarDto>> listCars(){
        List<CarDto> cars = carService.listCars()
                .stream()
                .map(carMapper::toDto)
                .toList();
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(
            @Valid @RequestBody CarRequest carRequest){
        CarEntity carToCreate = carMapper.toEntityFromRequest(carRequest);
        CarEntity savedCar = carService.createCar(carToCreate);
        return new ResponseEntity<>(
                carMapper.toDto(savedCar),
                HttpStatus.CREATED
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable("id") UUID id){
        CarEntity car = carService.getCar(id);
        CarDto carDto = carMapper.toDto(car);
        return ResponseEntity.ok(carDto);
    }
}
