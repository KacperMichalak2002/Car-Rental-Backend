package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.bodyType.BodyTypeRequest;
import com.Gr3ID12A.car_rental.domain.entities.BodyTypeEntity;
import com.Gr3ID12A.car_rental.mappers.BodyTypeMapper;
import com.Gr3ID12A.car_rental.repositories.BodyTypeRepository;
import com.Gr3ID12A.car_rental.services.impl.BodyTypeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BodyTypeServiceImplTest {

    @Mock
    private BodyTypeRepository bodyTypeRepository;

    @Mock
    private BodyTypeMapper bodyTypeMapper;

    @InjectMocks
    private BodyTypeServiceImpl bodyTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnListOfBodyTypes() {
        BodyTypeEntity entity = TestDataUtil.createTestBodyTypeEntity();
        BodyTypeDto dto = TestDataUtil.createTestBodyTypeDto();

        when(bodyTypeRepository.findAll()).thenReturn(List.of(entity));
        when(bodyTypeMapper.toDto(entity)).thenReturn(dto);

        List<BodyTypeDto> result = bodyTypeService.listBodyTypes();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void shouldCreateBodyType() {
        BodyTypeRequest request = new BodyTypeRequest("SUV");
        BodyTypeEntity entity = TestDataUtil.createTestBodyTypeEntity();
        BodyTypeDto dto = TestDataUtil.createTestBodyTypeDto();

        when(bodyTypeMapper.toEntity(request)).thenReturn(entity);
        when(bodyTypeRepository.save(entity)).thenReturn(entity);
        when(bodyTypeMapper.toDto(entity)).thenReturn(dto);

        BodyTypeDto result = bodyTypeService.createBodyType(request);

        assertEquals(dto, result);
    }

    @Test
    void shouldDeleteBodyTypeById() {
        UUID id = UUID.randomUUID();

        bodyTypeService.delete(id);

        verify(bodyTypeRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldReturnTrueIfBodyTypeExists() {
        UUID id = UUID.randomUUID();
        when(bodyTypeRepository.existsById(id)).thenReturn(true);

        assertTrue(bodyTypeService.isExist(id));
    }

    @Test
    void shouldReturnBodyTypeEntityById() {
        BodyTypeEntity entity = TestDataUtil.createTestBodyTypeEntity();
        UUID id = entity.getId();

        when(bodyTypeRepository.findById(id)).thenReturn(Optional.of(entity));

        BodyTypeEntity result = bodyTypeService.getBodyTypeById(id);

        assertEquals(entity, result);
    }

    @Test
    void shouldThrowExceptionWhenBodyTypeNotFound() {
        UUID id = UUID.randomUUID();

        when(bodyTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bodyTypeService.getBodyTypeById(id));
    }
}
