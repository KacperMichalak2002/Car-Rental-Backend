package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.car.CarDto;
import com.Gr3ID12A.car_rental.domain.dto.car.CarRequest;
import com.Gr3ID12A.car_rental.domain.entities.CarEntity;
import com.Gr3ID12A.car_rental.domain.entities.ModelEntity;
import com.Gr3ID12A.car_rental.domain.entities.SpecificationEntity;
import com.Gr3ID12A.car_rental.mappers.CarMapper;
import com.Gr3ID12A.car_rental.repositories.CarRepository;
import com.Gr3ID12A.car_rental.services.CarService;
import com.Gr3ID12A.car_rental.services.ModelService;
import com.Gr3ID12A.car_rental.services.SpecificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final ModelService modelService;
    private final SpecificationService specificationService;

    @Override
    public List<CarDto> listCars() {
        return carRepository.findAll()
                .stream()
                .map(carMapper::toDto)
                .toList();
    }

    @Override
    public CarDto createCar(CarRequest carRequest) {
        ModelEntity modelEntity = modelService.getModelEntityById(carRequest.getModelId());
        SpecificationEntity specificationEntity = specificationService.getSpecificationEntityById(carRequest.getSpecificationId());

        CarEntity carToCreate = carMapper.toEntityFromRequest(carRequest);
        carToCreate.setModel(modelEntity);
        carToCreate.setSpecification(specificationEntity);

        CarEntity savedCar = carRepository.save(carToCreate);
        return carMapper.toDto(savedCar);
    }

    @Override
    public CarDto getCar(UUID id) {
        Optional<CarEntity> foundCar = carRepository.findById(id);
        return foundCar.map(car -> {
            CarDto carDto = carMapper.toDto(car);
            return carDto;
        }).orElse(null);

    }
}
