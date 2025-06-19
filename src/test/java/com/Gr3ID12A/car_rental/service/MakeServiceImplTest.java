package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.make.MakeDto;
import com.Gr3ID12A.car_rental.domain.dto.make.MakeRequest;
import com.Gr3ID12A.car_rental.domain.entities.MakeEntity;
import com.Gr3ID12A.car_rental.mappers.MakeMapper;
import com.Gr3ID12A.car_rental.repositories.MakeRepository;
import com.Gr3ID12A.car_rental.services.impl.MakeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MakeServiceImplTest {

    private MakeRepository makeRepository;
    private MakeMapper makeMapper;
    private MakeServiceImpl makeService;

    @BeforeEach
    void setUp() {
        makeRepository = mock(MakeRepository.class);
        makeMapper = mock(MakeMapper.class);
        makeService = new MakeServiceImpl(makeRepository, makeMapper);
    }

    @Test
    void shouldReturnListOfMakes() {
        List<MakeEntity> entities = List.of(TestDataUtil.createTestMakeEntity());
        List<MakeDto> dtos = List.of(TestDataUtil.createTestMakeDto());

        when(makeRepository.findAll()).thenReturn(entities);
        when(makeMapper.toDto(any())).thenReturn(dtos.get(0));

        List<MakeDto> result = makeService.listMakes();

        assertEquals(dtos, result);
    }

    @Test
    void shouldCreateMake() {
        MakeRequest request = new MakeRequest("Volkswagen");
        MakeEntity entity = TestDataUtil.createTestMakeEntity();
        MakeDto dto = TestDataUtil.createTestMakeDto();

        when(makeMapper.toEntity(request)).thenReturn(entity);
        when(makeRepository.save(entity)).thenReturn(entity);
        when(makeMapper.toDto(entity)).thenReturn(dto);

        MakeDto result = makeService.createMake(request);

        assertEquals(dto, result);
    }

    @Test
    void shouldDeleteMakeById() {
        UUID id = UUID.randomUUID();

        makeService.delete(id);

        verify(makeRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldCheckIfMakeExists() {
        UUID id = UUID.randomUUID();
        when(makeRepository.existsById(id)).thenReturn(true);

        boolean exists = makeService.isExist(id);

        assertTrue(exists);
    }

    @Test
    void shouldReturnMakeEntityById() {
        UUID id = UUID.randomUUID();
        MakeEntity entity = TestDataUtil.createTestMakeEntity();

        when(makeRepository.findById(id)).thenReturn(Optional.of(entity));

        MakeEntity result = makeService.getMakeById(id);

        assertEquals(entity, result);
    }

    @Test
    void shouldThrowExceptionWhenMakeNotFound() {
        UUID id = UUID.randomUUID();
        when(makeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> makeService.getMakeById(id));
    }
}
