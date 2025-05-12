package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.CarDto;
import com.Gr3ID12A.car_rental.domain.entities.CarEntity;
import com.Gr3ID12A.car_rental.mappers.CarMapper;
import com.Gr3ID12A.car_rental.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestBody CarDto car){
        CarEntity carToCreate = carMapper.toEntity(car);
        CarEntity savedCar = carService.createCar(carToCreate);
        return new ResponseEntity<>(
                carMapper.toDto(savedCar),
                HttpStatus.CREATED
        );
    }
}
