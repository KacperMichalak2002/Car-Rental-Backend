package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.entities.CarEntity;

import java.util.List;

public interface CarService {

    List<CarEntity> listCars();
    CarEntity createCar(CarEntity car);
}
