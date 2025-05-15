package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.car.CarDto;
import com.Gr3ID12A.car_rental.domain.dto.car.CarRequest;
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

    @GetMapping
    public ResponseEntity<List<CarDto>> listCars(){
        List<CarDto> cars = carService.listCars();
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(
            @Valid @RequestBody CarRequest carRequest){
        CarDto savedCar = carService.createCar(carRequest);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable("id") UUID id){
        CarDto carDto = carService.getCar(id);
        if(carDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return ResponseEntity.ok(carDto);
        }
    }
}
