package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.domain.entities.PickUpPlaceEntity;
import com.Gr3ID12A.car_rental.mappers.PickUpPlaceMapper;
import com.Gr3ID12A.car_rental.repositories.PickUpPlaceRepository;
import com.Gr3ID12A.car_rental.services.impl.PickUpPlaceServiceImpl;
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

public class PickUpPlaceServiceImplTest {

    @Mock
    private PickUpPlaceRepository pickUpPlaceRepository;

    @Mock
    private PickUpPlaceMapper pickUpPlaceMapper;

    @InjectMocks
    private PickUpPlaceServiceImpl pickUpPlaceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListPickUpPlaces() {
        PickUpPlaceEntity entity = TestDataUtil.createTestPickUpPlaceEntity();
        PickUpPlaceDto dto = TestDataUtil.createTestPickUpPlaceDto();

        when(pickUpPlaceRepository.findAll()).thenReturn(List.of(entity));
        when(pickUpPlaceMapper.toDto(entity)).thenReturn(dto);

        List<PickUpPlaceDto> result = pickUpPlaceService.listPickUpPlaces();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(pickUpPlaceRepository).findAll();
        verify(pickUpPlaceMapper).toDto(entity);
    }

    @Test
    void testCreatePickUpPlace() {
        UUID addressId = UUID.randomUUID();
        PickUpPlaceRequest request = TestDataUtil.createTestPickUpPlace(addressId);
        PickUpPlaceEntity entityToSave = new PickUpPlaceEntity();
        PickUpPlaceEntity savedEntity = TestDataUtil.createTestPickUpPlaceEntity();
        PickUpPlaceDto expectedDto = TestDataUtil.createTestPickUpPlaceDto();


        when(pickUpPlaceMapper.toEntity(request)).thenReturn(entityToSave);
        when(pickUpPlaceRepository.save(entityToSave)).thenReturn(savedEntity);
        when(pickUpPlaceMapper.toDto(savedEntity)).thenReturn(expectedDto);

        PickUpPlaceDto result = pickUpPlaceService.createPickUpPlace(request);

        assertEquals(expectedDto, result);
        assertEquals(addressId, entityToSave.getAddress().getId());
        verify(pickUpPlaceMapper).toEntity(request);
        verify(pickUpPlaceRepository).save(entityToSave);
        verify(pickUpPlaceMapper).toDto(savedEntity);
    }

    @Test
    void testGetPickUpPlaceById_Found() {
        UUID id = UUID.randomUUID();
        PickUpPlaceEntity entity = TestDataUtil.createTestPickUpPlaceEntity();

        when(pickUpPlaceRepository.findById(id)).thenReturn(Optional.of(entity));

        PickUpPlaceEntity result = pickUpPlaceService.getPickUpPlaceById(id);

        assertEquals(entity, result);
        verify(pickUpPlaceRepository).findById(id);
    }

    @Test
    void testGetPickUpPlaceById_NotFound() {
        UUID id = UUID.randomUUID();

        when(pickUpPlaceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pickUpPlaceService.getPickUpPlaceById(id));
        verify(pickUpPlaceRepository).findById(id);
    }

    @Test
    void testIsExist() {
        UUID id = UUID.randomUUID();
        when(pickUpPlaceRepository.existsById(id)).thenReturn(true);

        boolean result = pickUpPlaceService.isExist(id);

        assertTrue(result);
        verify(pickUpPlaceRepository).existsById(id);
    }
}
