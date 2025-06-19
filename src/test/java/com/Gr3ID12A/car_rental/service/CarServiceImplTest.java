package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.car.CarDto;
import com.Gr3ID12A.car_rental.domain.dto.car.CarRequest;
import com.Gr3ID12A.car_rental.domain.entities.CarEntity;
import com.Gr3ID12A.car_rental.mappers.CarMapper;
import com.Gr3ID12A.car_rental.repositories.CarRepository;
import com.Gr3ID12A.car_rental.services.impl.CarServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Mock private CarRepository carRepository;
    @Mock private CarMapper carMapper;

    @InjectMocks private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnListOfCars() {
        CarEntity entity = TestDataUtil.createTestCarEntity();
        CarDto dto = TestDataUtil.createTestCarDto();

        when(carRepository.findAll()).thenReturn(List.of(entity));
        when(carMapper.toDto(entity)).thenReturn(dto);

        List<CarDto> result = carService.listCars();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void shouldCreateCar() {
        CarRequest request = TestDataUtil.createTestCarRequest();
        CarEntity carToCreate = TestDataUtil.createTestCarEntity();
        CarDto dto = TestDataUtil.createTestCarDto();

        when(carMapper.toEntityFromRequest(request)).thenReturn(carToCreate);
        when(carRepository.save(carToCreate)).thenReturn(carToCreate);
        when(carMapper.toDto(carToCreate)).thenReturn(dto);

        CarDto result = carService.createCar(request);

        assertEquals(dto, result);
    }

    @Test
    void shouldReturnCarById() {
        CarEntity entity = TestDataUtil.createTestCarEntity();
        CarDto dto = TestDataUtil.createTestCarDto();
        UUID id = entity.getId();

        when(carRepository.findById(id)).thenReturn(Optional.of(entity));
        when(carMapper.toDto(entity)).thenReturn(dto);

        CarDto result = carService.getCar(id);

        assertEquals(dto, result);
    }

    @Test
    void shouldReturnNullWhenCarNotFound() {
        UUID id = UUID.randomUUID();
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        CarDto result = carService.getCar(id);

        assertNull(result);
    }

    @Test
    void shouldCheckIfCarExists() {
        UUID id = UUID.randomUUID();
        when(carRepository.existsById(id)).thenReturn(true);

        assertTrue(carService.isExist(id));
    }

    @Test
    void shouldUpdateCarPartially() {
        UUID id = UUID.randomUUID();
        CarRequest request = TestDataUtil.createTestCarRequest();
        CarEntity existing = TestDataUtil.createTestCarEntity();
        CarEntity updated = TestDataUtil.createTestCarEntity();
        CarDto dto = TestDataUtil.createTestCarDto();

        when(carMapper.toEntityFromRequest(request)).thenReturn(updated);
        when(carRepository.findById(id)).thenReturn(Optional.of(existing));
        when(carRepository.save(existing)).thenReturn(existing);
        when(carMapper.toDto(existing)).thenReturn(dto);

        CarDto result = carService.partialUpdateCar(id, request);

        assertEquals(dto, result);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingCar() {
        UUID id = UUID.randomUUID();
        CarRequest request = TestDataUtil.createTestCarRequest();

        when(carRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.partialUpdateCar(id, request));
    }

    @Test
    void shouldDeleteCar() {
        UUID id = UUID.randomUUID();

        carService.delete(id);

        verify(carRepository).deleteById(id);
    }

    @Test
    void shouldReturnCarEntityById() {
        CarEntity entity = TestDataUtil.createTestCarEntity();
        UUID id = entity.getId();

        when(carRepository.findById(id)).thenReturn(Optional.of(entity));

        CarEntity result = carService.getCarEntityById(id);

        assertEquals(entity, result);
    }

    @Test
    void shouldThrowExceptionWhenCarEntityNotFound() {
        UUID id = UUID.randomUUID();
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.getCarEntityById(id));
    }
}
