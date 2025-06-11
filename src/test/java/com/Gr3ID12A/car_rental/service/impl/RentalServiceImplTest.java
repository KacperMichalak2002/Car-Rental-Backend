package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalRequest;
import com.Gr3ID12A.car_rental.domain.entities.RentalEntity;
import com.Gr3ID12A.car_rental.mappers.RentalMapper;
import com.Gr3ID12A.car_rental.repositories.RentalRepository;
import com.Gr3ID12A.car_rental.services.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RentalServiceImplTest {

    private RentalRepository rentalRepository;
    private RentalMapper rentalMapper;
    private CustomerService customerService;
    private CarService carService;
    private PickUpPlaceService pickUpPlaceService;
    private ReturnPlaceService returnPlaceService;

    private RentalServiceImpl rentalService;

    @BeforeEach
    void setUp() {
        rentalRepository = mock(RentalRepository.class);
        rentalMapper = mock(RentalMapper.class);
        customerService = mock(CustomerService.class);
        carService = mock(CarService.class);
        pickUpPlaceService = mock(PickUpPlaceService.class);
        returnPlaceService = mock(ReturnPlaceService.class);

        rentalService = new RentalServiceImpl(
                rentalMapper,
                rentalRepository,
                customerService,
                carService,
                pickUpPlaceService,
                returnPlaceService
        );
    }

    /**
     * Test jednostkowy metody createRental().
     * Sprawdza, czy przy poprawnych danych wejściowych
     * tworzony jest obiekt wypożyczenia i zwracany DTO.
     */
    @Test
    void shouldCreateRentalSuccessfullyWhenDataIsValid() {
        // given
        UUID customerId = UUID.randomUUID();
        UUID carId = UUID.randomUUID();
        UUID pickUpPlaceId = UUID.randomUUID();
        UUID returnPlaceId = UUID.randomUUID();

        RentalRequest request = new RentalRequest();
        request.setCustomerId(customerId);
        request.setCarId(carId);
        request.setPick_up_placeId(pickUpPlaceId);
        request.setReturn_placeId(returnPlaceId);

        RentalEntity entity = new RentalEntity();
        RentalEntity savedEntity = new RentalEntity();
        RentalDto dto = new RentalDto();

        when(rentalMapper.toEntity(request)).thenReturn(entity);
        when(rentalRepository.save(entity)).thenReturn(savedEntity);
        when(rentalMapper.toDto(savedEntity)).thenReturn(dto);

        // when
        RentalDto result = rentalService.createRental(request);

        // then
        assertNotNull(result);
        verify(rentalMapper).toEntity(request);
        verify(rentalRepository).save(entity);
        verify(rentalMapper).toDto(savedEntity);
    }

    /**
     * Test jednostkowy metody listRentalsByCustomer().
     * Sprawdza, czy dla danego ID klienta zwracana jest lista wypożyczeń.
     */
    @Test
    void shouldReturnListOfRentalsByCustomerId() {
        // given
        UUID customerId = UUID.randomUUID();
        RentalEntity entity1 = new RentalEntity();
        RentalEntity entity2 = new RentalEntity();
        RentalDto dto1 = new RentalDto();
        RentalDto dto2 = new RentalDto();

        when(rentalRepository.findAllByCustomer_Id(customerId)).thenReturn(List.of(entity1, entity2));
        when(rentalMapper.toDto(entity1)).thenReturn(dto1);
        when(rentalMapper.toDto(entity2)).thenReturn(dto2);

        // when
        List<RentalDto> result = rentalService.listRentalsByCustomer(customerId);

        // then
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        verify(rentalRepository).findAllByCustomer_Id(customerId);
    }
}
