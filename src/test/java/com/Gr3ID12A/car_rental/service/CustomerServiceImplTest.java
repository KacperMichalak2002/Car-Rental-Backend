package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerDto;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerRequest;
import com.Gr3ID12A.car_rental.domain.entities.CustomerEntity;
import com.Gr3ID12A.car_rental.mappers.CustomerMapper;
import com.Gr3ID12A.car_rental.repositories.CustomerRepository;
import com.Gr3ID12A.car_rental.services.impl.CustomerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnListOfCustomers() {
        CustomerEntity entity = TestDataUtil.createTestCustomerEntity();
        CustomerDto dto = TestDataUtil.createTestCustomerDto();

        when(customerRepository.findAll()).thenReturn(List.of(entity));
        when(customerMapper.toDto(entity)).thenReturn(dto);

        List<CustomerDto> result = customerService.listCustomers();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
    }

    @Test
    void shouldReturnCustomerById() {
        CustomerEntity entity = TestDataUtil.createTestCustomerEntity();
        CustomerDto dto = TestDataUtil.createTestCustomerDto();
        UUID id = entity.getId();

        when(customerRepository.findById(id)).thenReturn(Optional.of(entity));
        when(customerMapper.toDto(entity)).thenReturn(dto);

        CustomerDto result = customerService.getCustomer(id);

        assertEquals(dto, result);
    }

    @Test
    void shouldReturnNullWhenCustomerNotFound() {
        UUID id = UUID.randomUUID();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        CustomerDto result = customerService.getCustomer(id);

        assertNull(result);
    }

    @Test
    void shouldCreateCustomer() {
        CustomerRequest request = TestDataUtil.createTestCustomerRequest();
        CustomerEntity entity = TestDataUtil.createTestCustomerEntity();
        CustomerDto dto = TestDataUtil.createTestCustomerDto();

        when(customerMapper.toEntity(request)).thenReturn(entity);
        when(customerRepository.save(entity)).thenReturn(entity);
        when(customerMapper.toDto(entity)).thenReturn(dto);

        CustomerDto result = customerService.createCustomer(request);

        assertEquals(dto, result);
    }

    @Test
    void shouldCheckIfCustomerExists() {
        UUID id = UUID.randomUUID();
        when(customerRepository.existsById(id)).thenReturn(true);

        assertTrue(customerService.isExist(id));
    }

    @Test
    void shouldUpdateCustomerPartially() {
        UUID id = UUID.randomUUID();
        CustomerRequest request = TestDataUtil.createTestCustomerRequest();
        CustomerEntity existing = TestDataUtil.createTestCustomerEntity();
        CustomerEntity updated = TestDataUtil.createTestCustomerEntity();
        CustomerDto dto = TestDataUtil.createTestCustomerDto();

        when(customerMapper.toEntity(request)).thenReturn(updated);
        when(customerRepository.findById(id)).thenReturn(Optional.of(existing));
        when(customerRepository.save(existing)).thenReturn(existing);
        when(customerMapper.toDto(existing)).thenReturn(dto);

        CustomerDto result = customerService.partialUpdate(id, request);

        assertEquals(dto, result);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingCustomer() {
        UUID id = UUID.randomUUID();
        CustomerRequest request = TestDataUtil.createTestCustomerRequest();

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> customerService.partialUpdate(id, request));
    }

}
