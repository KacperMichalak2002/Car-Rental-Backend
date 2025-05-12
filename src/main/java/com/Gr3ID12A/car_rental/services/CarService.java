package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.entities.CarEntity;

import java.util.List;
import java.util.UUID;

public interface CarService {

    List<CarEntity> listCars();
    CarEntity createCar(CarEntity car);
    CarEntity getCar(UUID id);
}
