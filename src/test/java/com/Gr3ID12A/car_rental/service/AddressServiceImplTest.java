package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.address.AddressDto;
import com.Gr3ID12A.car_rental.domain.dto.address.AddressRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.mappers.AddressMapper;
import com.Gr3ID12A.car_rental.repositories.AddressRepository;
import com.Gr3ID12A.car_rental.services.impl.AddressServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnListOfAddresses() {
        AddressEntity entity = TestDataUtil.createTestAddressEntity();
        AddressDto dto = TestDataUtil.createTestAddressDto();

        when(addressRepository.findAll()).thenReturn(List.of(entity));
        when(addressMapper.toDto(entity)).thenReturn(dto);

        List<AddressDto> result = addressService.listAddresses();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void shouldReturnAddressById() {
        AddressEntity entity = TestDataUtil.createTestAddressEntity();
        AddressDto dto = TestDataUtil.createTestAddressDto();
        UUID id = entity.getId();

        when(addressRepository.findById(id)).thenReturn(Optional.of(entity));
        when(addressMapper.toDto(entity)).thenReturn(dto);

        AddressDto result = addressService.getAddress(id);

        assertEquals(dto, result);
    }

    @Test
    void shouldReturnNullWhenAddressNotFound() {
        UUID id = UUID.randomUUID();
        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        AddressDto result = addressService.getAddress(id);

        assertNull(result);
    }

    @Test
    void shouldCreateAddress() {
        AddressRequest request = TestDataUtil.createTestAddressRequest();
        AddressEntity entity = TestDataUtil.createTestAddressEntity();
        AddressDto dto = TestDataUtil.createTestAddressDto();

        when(addressMapper.toEntity(request)).thenReturn(entity);
        when(addressRepository.save(entity)).thenReturn(entity);
        when(addressMapper.toDto(entity)).thenReturn(dto);

        AddressDto result = addressService.createAddress(request);

        assertEquals(dto, result);
    }

    @Test
    void shouldCheckIfAddressExists() {
        UUID id = UUID.randomUUID();
        when(addressRepository.existsById(id)).thenReturn(true);

        assertTrue(addressService.isExist(id));
    }

    @Test
    void shouldUpdateAddressPartially() {
        UUID id = UUID.randomUUID();
        AddressRequest request = TestDataUtil.createTestAddressRequest();
        AddressEntity existing = TestDataUtil.createTestAddressEntity();
        AddressEntity updated = TestDataUtil.createTestAddressEntity();
        AddressDto dto = TestDataUtil.createTestAddressDto();

        when(addressMapper.toEntity(request)).thenReturn(updated);
        when(addressRepository.findById(id)).thenReturn(Optional.of(existing));
        when(addressRepository.save(existing)).thenReturn(existing);
        when(addressMapper.toDto(existing)).thenReturn(dto);

        AddressDto result = addressService.partialUpdate(id, request);

        assertEquals(dto, result);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingAddress() {
        UUID id = UUID.randomUUID();
        AddressRequest request = TestDataUtil.createTestAddressRequest();

        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.partialUpdate(id, request));
    }

    @Test
    void shouldReturnAddressEntityById() {
        AddressEntity entity = TestDataUtil.createTestAddressEntity();
        UUID id = entity.getId();

        when(addressRepository.findById(id)).thenReturn(Optional.of(entity));

        AddressEntity result = addressService.getAddressEntityById(id);

        assertEquals(entity, result);
    }

    @Test
    void shouldThrowExceptionWhenAddressEntityNotFound() {
        UUID id = UUID.randomUUID();
        when(addressRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> addressService.getAddressEntityById(id));
    }
}
