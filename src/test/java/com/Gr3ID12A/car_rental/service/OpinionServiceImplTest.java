package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.opinion.OpinionDto;
import com.Gr3ID12A.car_rental.domain.dto.opinion.OpinionRequest;
import com.Gr3ID12A.car_rental.domain.entities.OpinionEntity;
import com.Gr3ID12A.car_rental.mappers.OpinionMapper;
import com.Gr3ID12A.car_rental.repositories.OpinionRepository;
import com.Gr3ID12A.car_rental.services.CarService;
import com.Gr3ID12A.car_rental.services.CustomerService;
import com.Gr3ID12A.car_rental.services.impl.OpinionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OpinionServiceImplTest {

    private OpinionRepository opinionRepository;
    private OpinionMapper opinionMapper;
    private CustomerService customerService;
    private CarService carService;
    private OpinionServiceImpl opinionService;

    @BeforeEach
    void setUp() {
        opinionRepository = mock(OpinionRepository.class);
        opinionMapper = mock(OpinionMapper.class);
        customerService = mock(CustomerService.class);
        carService = mock(CarService.class);
        opinionService = new OpinionServiceImpl(opinionRepository, opinionMapper, customerService, carService);
    }

    @Test
    void shouldListOpinions() {
        OpinionEntity entity = new OpinionEntity();
        OpinionDto dto = new OpinionDto();

        when(opinionRepository.findAll()).thenReturn(List.of(entity));
        when(opinionMapper.toDto(entity)).thenReturn(dto);

        List<OpinionDto> result = opinionService.listOpinions();

        assertEquals(1, result.size());
        verify(opinionRepository).findAll();
    }

    @Test
    void shouldCreateOpinion() {
        OpinionRequest request = TestDataUtil.createTestOpinionRequest();
        OpinionEntity entity = new OpinionEntity();
        OpinionEntity savedEntity = new OpinionEntity();
        OpinionDto expectedDto = new OpinionDto();

        when(opinionMapper.toEntity(request)).thenReturn(entity);
        when(opinionRepository.save(entity)).thenReturn(savedEntity);
        when(opinionMapper.toDto(savedEntity)).thenReturn(expectedDto);

        OpinionDto result = opinionService.createOpinion(request);

        assertEquals(expectedDto, result);
        verify(opinionRepository).save(entity);
    }

    @Test
    void shouldPartiallyUpdateOpinion() {
        UUID id = UUID.randomUUID();
        OpinionRequest request = TestDataUtil.createTestOpinionRequest();
        OpinionEntity entity = new OpinionEntity();
        OpinionEntity existing = new OpinionEntity();
        OpinionDto dto = new OpinionDto();

        entity.setRating(request.getRating());
        entity.setDescription(request.getDescription());

        when(opinionMapper.toEntity(request)).thenReturn(entity);
        when(opinionRepository.findById(id)).thenReturn(Optional.of(existing));
        when(opinionRepository.save(existing)).thenReturn(existing);
        when(opinionMapper.toDto(existing)).thenReturn(dto);

        OpinionDto result = opinionService.partialUpdate(id, request);

        assertEquals(dto, result);
        verify(opinionRepository).save(existing);
    }

    @Test
    void shouldDeleteOpinion() {
        UUID id = UUID.randomUUID();
        opinionService.delete(id);
        verify(opinionRepository).deleteById(id);
    }

    @Test
    void shouldCheckIfOpinionExists() {
        UUID id = UUID.randomUUID();
        when(opinionRepository.existsById(id)).thenReturn(true);
        assertTrue(opinionService.isExist(id));
    }

    @Test
    void shouldListOpinionsByCarId() {
        UUID carId = UUID.randomUUID();
        OpinionEntity entity = new OpinionEntity();
        OpinionDto dto = new OpinionDto();

        when(opinionRepository.findAllByCar_Id(carId)).thenReturn(List.of(entity));
        when(opinionMapper.toDto(entity)).thenReturn(dto);

        List<OpinionDto> result = opinionService.listOpinionsByCar(carId);

        assertEquals(1, result.size());
    }
}
