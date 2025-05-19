package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.car.CarDto;
import com.Gr3ID12A.car_rental.domain.dto.car.CarRequest;
import com.Gr3ID12A.car_rental.domain.entities.CarEntity;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public interface CarService {

    List<CarDto> listCars();
    CarDto createCar(CarRequest carRequest);
    CarDto getCar(UUID id);

    boolean isExist(UUID id);

    CarDto partialUpdateCar(UUID id, CarRequest carRequest);

    void delete(UUID id);

    CarEntity getCarEntityById(@NotNull UUID carId);
}
