package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.car.CarDto;
import com.Gr3ID12A.car_rental.domain.dto.car.CarRequest;
import com.Gr3ID12A.car_rental.services.CarService;
import com.Gr3ID12A.car_rental.services.ModelService;
import com.Gr3ID12A.car_rental.services.SpecificationService;
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
    private final ModelService modelService;
    private final SpecificationService specificationService;

    @GetMapping
    public ResponseEntity<List<CarDto>> listCars(){
        List<CarDto> cars = carService.listCars();
        return ResponseEntity.ok(cars);
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(
            @Valid @RequestBody CarRequest carRequest){

        boolean modelExist = modelService.isExist(carRequest.getModelId());
        boolean specificationExist = specificationService.isExist(carRequest.getSpecificationId());

        if(!modelExist || !specificationExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

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

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CarDto> partialUpdate(@PathVariable("id") UUID id, @RequestBody CarRequest carRequest){
        boolean carExist = carService.isExist(id);
        if(!carExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CarDto updatedCar = carService.partialUpdateCar(id, carRequest);
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") UUID id){
        carService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
