package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.model.ModelDto;
import com.Gr3ID12A.car_rental.domain.dto.model.ModelRequest;
import com.Gr3ID12A.car_rental.domain.entities.BodyTypeEntity;
import com.Gr3ID12A.car_rental.domain.entities.MakeEntity;
import com.Gr3ID12A.car_rental.domain.entities.ModelEntity;
import com.Gr3ID12A.car_rental.mappers.ModelMapper;
import com.Gr3ID12A.car_rental.repositories.ModelRepository;
import com.Gr3ID12A.car_rental.services.impl.ModelServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ModelServiceImplTest {

    private ModelRepository modelRepository;
    private ModelMapper modelMapper;
    private ModelServiceImpl modelService;

    @BeforeEach
    void setUp() {
        modelRepository = mock(ModelRepository.class);
        modelMapper = mock(ModelMapper.class);
        modelService = new ModelServiceImpl(modelRepository, modelMapper);
    }

    @Test
    void shouldListAllModels() {
        List<ModelEntity> entities = List.of(TestDataUtil.createTestModelEntityA());
        List<ModelDto> dtos = List.of(TestDataUtil.createTestModelDto());

        when(modelRepository.findAll()).thenReturn(entities);
        when(modelMapper.toDto(any(ModelEntity.class))).thenReturn(dtos.get(0));

        List<ModelDto> result = modelService.listModels();

        assertEquals(dtos.size(), result.size());
        assertEquals(dtos.get(0).getName(), result.get(0).getName());
    }

    @Test
    void shouldCreateModel() {
        ModelRequest request = TestDataUtil.createTestModelRequest();
        ModelEntity modelEntity = TestDataUtil.createTestModelEntityA();

        when(modelMapper.toEntity(request)).thenReturn(modelEntity);
        when(modelRepository.save(any(ModelEntity.class))).thenReturn(modelEntity);
        when(modelMapper.toDto(modelEntity)).thenReturn(TestDataUtil.createTestModelDto());

        ModelDto result = modelService.createModel(request);

        assertNotNull(result);
        assertEquals("Touran", result.getName());
    }

    @Test
    void shouldCheckIfModelExists() {
        UUID id = UUID.randomUUID();
        when(modelRepository.existsById(id)).thenReturn(true);

        assertTrue(modelService.isExist(id));
    }

    @Test
    void shouldDeleteModelById() {
        UUID id = UUID.randomUUID();
        modelService.delete(id);

        verify(modelRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldGetModelEntityById() {
        UUID id = UUID.randomUUID();
        ModelEntity entity = TestDataUtil.createTestModelEntityA();

        when(modelRepository.findById(id)).thenReturn(Optional.of(entity));

        ModelEntity result = modelService.getModelEntityById(id);

        assertEquals(entity.getName(), result.getName());
    }

    @Test
    void shouldThrowWhenModelNotFound() {
        UUID id = UUID.randomUUID();
        when(modelRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> modelService.getModelEntityById(id));
    }
}
