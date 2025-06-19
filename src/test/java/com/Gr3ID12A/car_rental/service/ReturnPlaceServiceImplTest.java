package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.ReturnPlaceEntity;
import com.Gr3ID12A.car_rental.mappers.ReturnPlaceMapper;
import com.Gr3ID12A.car_rental.repositories.ReturnPlaceRepository;
import com.Gr3ID12A.car_rental.services.impl.ReturnPlaceServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReturnPlaceServiceImplTest {

    @Mock
    private ReturnPlaceRepository returnPlaceRepository;

    @Mock
    private ReturnPlaceMapper returnPlaceMapper;

    @InjectMocks
    private ReturnPlaceServiceImpl returnPlaceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldListReturnPlaces() {
        ReturnPlaceEntity entity = TestDataUtil.createTestReturnPlaceEntity();
        ReturnPlaceDto dto = TestDataUtil.createTestReturnPlaceDto();

        when(returnPlaceRepository.findAll()).thenReturn(List.of(entity));
        when(returnPlaceMapper.toDto(entity)).thenReturn(dto);

        List<ReturnPlaceDto> result = returnPlaceService.listReturnPlaces();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void shouldCreateReturnPlace() {
        ReturnPlaceRequest request = ReturnPlaceRequest.builder()
                .addressId(UUID.randomUUID())
                .build();

        ReturnPlaceEntity entityToSave = new ReturnPlaceEntity();
        ReturnPlaceEntity savedEntity = TestDataUtil.createTestReturnPlaceEntity();
        ReturnPlaceDto expectedDto = TestDataUtil.createTestReturnPlaceDto();

        when(returnPlaceMapper.toEntity(request)).thenReturn(entityToSave);
        when(returnPlaceRepository.save(entityToSave)).thenReturn(savedEntity);
        when(returnPlaceMapper.toDto(savedEntity)).thenReturn(expectedDto);

        ReturnPlaceDto result = returnPlaceService.createReturnPlace(request);

        assertEquals(expectedDto, result);
    }

    @Test
    void shouldGetReturnPlaceEntityById() {
        ReturnPlaceEntity entity = TestDataUtil.createTestReturnPlaceEntity();
        UUID id = entity.getId();

        when(returnPlaceRepository.findById(id)).thenReturn(Optional.of(entity));

        ReturnPlaceEntity result = returnPlaceService.getReturnPlaceEntityById(id);

        assertEquals(entity, result);
    }

    @Test
    void shouldThrowWhenReturnPlaceNotFound() {
        UUID id = UUID.randomUUID();

        when(returnPlaceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> returnPlaceService.getReturnPlaceEntityById(id));
    }

    @Test
    void shouldCheckIfReturnPlaceExists() {
        UUID id = UUID.randomUUID();

        when(returnPlaceRepository.existsById(id)).thenReturn(true);

        boolean exists = returnPlaceService.isExist(id);

        assertTrue(exists);
    }
}
