package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.entities.CarEntity;
import com.Gr3ID12A.car_rental.repositories.CarRepository;
import com.Gr3ID12A.car_rental.services.CarService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public List<CarEntity> listCars() {
        return carRepository.findAll();
    }

    @Override
    public CarEntity createCar(CarEntity car) {
        return carRepository.save(car);
    }

    @Override
    public CarEntity getCar(UUID id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car doesn't exists with Id " + id));
    }
}
