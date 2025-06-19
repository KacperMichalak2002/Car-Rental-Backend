package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalRequest;
import com.Gr3ID12A.car_rental.domain.entities.RentalEntity;
import com.Gr3ID12A.car_rental.mappers.RentalMapper;
import com.Gr3ID12A.car_rental.repositories.RentalRepository;
import com.Gr3ID12A.car_rental.services.*;
import com.Gr3ID12A.car_rental.services.impl.RentalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RentalServiceImplTest {

    @Mock
    private RentalMapper rentalMapper;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private CarService carService;

    @Mock
    private PickUpPlaceService pickUpPlaceService;

    @Mock
    private ReturnPlaceService returnPlaceService;

    @InjectMocks
    private RentalServiceImpl rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldListRentals() {
        List<RentalEntity> rentalEntities = List.of(TestDataUtil.createRentalEntity());
        List<RentalDto> rentalDtos = List.of(TestDataUtil.createTestRentalDto());

        when(rentalRepository.findAll()).thenReturn(rentalEntities);
        when(rentalMapper.toDto(any())).thenReturn(rentalDtos.get(0));

        List<RentalDto> result = rentalService.listRentals();

        assertEquals(1, result.size());
        assertEquals(rentalDtos.get(0), result.get(0));
        verify(rentalRepository, times(1)).findAll();
    }

    @Test
    void shouldListRentalsByCustomer() {
        UUID customerId = UUID.randomUUID();
        List<RentalEntity> rentalEntities = List.of(TestDataUtil.createRentalEntity());
        List<RentalDto> rentalDtos = List.of(TestDataUtil.createTestRentalDto());

        when(rentalRepository.findAllByCustomer_Id(customerId)).thenReturn(rentalEntities);
        when(rentalMapper.toDto(any())).thenReturn(rentalDtos.get(0));

        List<RentalDto> result = rentalService.listRentalsByCustomer(customerId);

        assertEquals(1, result.size());
        assertEquals(rentalDtos.get(0), result.get(0));
        verify(rentalRepository, times(1)).findAllByCustomer_Id(customerId);
    }

    @Test
    void shouldCreateRental() {
        RentalRequest rentalRequest = TestDataUtil.createTestRentalRequest();
        RentalEntity rentalEntity = TestDataUtil.createRentalEntity();
        RentalDto rentalDto = TestDataUtil.createTestRentalDto();

        when(rentalMapper.toEntity(rentalRequest)).thenReturn(rentalEntity);
        when(rentalRepository.save(rentalEntity)).thenReturn(rentalEntity);
        when(rentalMapper.toDto(rentalEntity)).thenReturn(rentalDto);

        RentalDto result = rentalService.createRental(rentalRequest);

        assertEquals(rentalDto, result);
        verify(rentalRepository, times(1)).save(rentalEntity);
    }

    @Test
    void shouldDeleteRental() {
        UUID rentalId = UUID.randomUUID();

        rentalService.delete(rentalId);

        verify(rentalRepository, times(1)).deleteById(rentalId);
    }

    @Test
    void shouldCheckRentalExistence() {
        UUID rentalId = UUID.randomUUID();

        when(rentalRepository.existsById(rentalId)).thenReturn(true);

        boolean result = rentalService.isExist(rentalId);

        assertEquals(true, result);
        verify(rentalRepository, times(1)).existsById(rentalId);
    }
}
