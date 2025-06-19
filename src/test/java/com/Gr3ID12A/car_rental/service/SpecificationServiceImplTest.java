package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationDto;
import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationRequest;
import com.Gr3ID12A.car_rental.domain.entities.SpecificationEntity;
import com.Gr3ID12A.car_rental.mappers.SpecificationMapper;
import com.Gr3ID12A.car_rental.repositories.SpecificationRepository;
import com.Gr3ID12A.car_rental.services.impl.SpecificationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SpecificationServiceImplTest {

    @Mock
    private SpecificationRepository specificationRepository;

    @Mock
    private SpecificationMapper specificationMapper;

    @InjectMocks
    private SpecificationServiceImpl specificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldListSpecifications() {
        SpecificationEntity entity = TestDataUtil.createTestSpecificationEntity();
        SpecificationDto dto = TestDataUtil.createTestSpecificationDto();

        when(specificationRepository.findAll()).thenReturn(List.of(entity));
        when(specificationMapper.toDto(entity)).thenReturn(dto);

        List<SpecificationDto> result = specificationService.listSpecifications();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void shouldReturnSpecificationByIdIfExists() {
        SpecificationEntity entity = TestDataUtil.createTestSpecificationEntity();
        SpecificationDto dto = TestDataUtil.createTestSpecificationDto();
        UUID id = entity.getId();

        when(specificationRepository.findById(id)).thenReturn(Optional.of(entity));
        when(specificationMapper.toDto(entity)).thenReturn(dto);

        SpecificationDto result = specificationService.getSpecification(id);

        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void shouldReturnNullIfSpecificationNotExists() {
        UUID id = UUID.randomUUID();
        when(specificationRepository.findById(id)).thenReturn(Optional.empty());

        SpecificationDto result = specificationService.getSpecification(id);

        assertNull(result);
    }

    @Test
    void shouldCreateSpecification() {
        SpecificationRequest request = TestDataUtil.createTestSpecificationRequest();
        SpecificationEntity toCreate = new SpecificationEntity();
        SpecificationEntity saved = TestDataUtil.createTestSpecificationEntity();
        SpecificationDto dto = TestDataUtil.createTestSpecificationDto();

        when(specificationMapper.toEntity(request)).thenReturn(toCreate);
        when(specificationRepository.save(toCreate)).thenReturn(saved);
        when(specificationMapper.toDto(saved)).thenReturn(dto);

        SpecificationDto result = specificationService.createSpecification(request);

        assertEquals(dto, result);
    }

    @Test
    void shouldCheckIfSpecificationExists() {
        UUID id = UUID.randomUUID();
        when(specificationRepository.existsById(id)).thenReturn(true);

        boolean exists = specificationService.isExist(id);

        assertTrue(exists);
    }

    @Test
    void shouldReturnSpecificationEntityByIdIfExists() {
        SpecificationEntity entity = TestDataUtil.createTestSpecificationEntity();
        UUID id = entity.getId();

        when(specificationRepository.findById(id)).thenReturn(Optional.of(entity));

        SpecificationEntity result = specificationService.getSpecificationEntityById(id);

        assertEquals(entity, result);
    }

    @Test
    void shouldThrowWhenSpecificationNotFound() {
        UUID id = UUID.randomUUID();
        when(specificationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> specificationService.getSpecificationEntityById(id));
    }
}
