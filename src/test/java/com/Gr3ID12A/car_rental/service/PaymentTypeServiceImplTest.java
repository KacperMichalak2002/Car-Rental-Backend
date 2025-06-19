package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeRequest;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentTypeEntity;
import com.Gr3ID12A.car_rental.mappers.PaymentTypeMapper;
import com.Gr3ID12A.car_rental.repositories.PaymentTypeRepository;
import com.Gr3ID12A.car_rental.services.impl.PaymentTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PaymentTypeServiceImplTest {

    @Mock
    private PaymentTypeRepository paymentTypeRepository;

    @Mock
    private PaymentTypeMapper paymentTypeMapper;

    @InjectMocks
    private PaymentTypeServiceImpl paymentTypeService;

    private PaymentTypeEntity paymentTypeEntity;
    private PaymentTypeDto paymentTypeDto;
    private PaymentTypeRequest paymentTypeRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paymentTypeEntity = TestDataUtil.createTestOnlinePaymentType();
        paymentTypeDto = TestDataUtil.createTestOnlinePaymentTypeDto();
        paymentTypeRequest = new PaymentTypeRequest("ONLINE");
    }

    @Test
    void shouldListAllPaymentTypes() {
        when(paymentTypeRepository.findAll()).thenReturn(List.of(paymentTypeEntity));
        when(paymentTypeMapper.toDto(paymentTypeEntity)).thenReturn(paymentTypeDto);

        List<PaymentTypeDto> result = paymentTypeService.listPaymentType();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly(paymentTypeDto);

        verify(paymentTypeRepository).findAll();
        verify(paymentTypeMapper).toDto(paymentTypeEntity);
    }

    @Test
    void shouldCreatePaymentType() {
        when(paymentTypeMapper.toEntity(paymentTypeRequest)).thenReturn(paymentTypeEntity);
        when(paymentTypeRepository.save(paymentTypeEntity)).thenReturn(paymentTypeEntity);
        when(paymentTypeMapper.toDto(paymentTypeEntity)).thenReturn(paymentTypeDto);

        PaymentTypeDto result = paymentTypeService.createPaymentType(paymentTypeRequest);

        assertThat(result).isEqualTo(paymentTypeDto);

        verify(paymentTypeMapper).toEntity(paymentTypeRequest);
        verify(paymentTypeRepository).save(paymentTypeEntity);
        verify(paymentTypeMapper).toDto(paymentTypeEntity);
    }

    @Test
    void shouldReturnTrueIfPaymentTypeExists() {
        UUID id = UUID.randomUUID();
        when(paymentTypeRepository.existsById(id)).thenReturn(true);

        boolean exists = paymentTypeService.isExist(id);

        assertThat(exists).isTrue();
        verify(paymentTypeRepository).existsById(id);
    }

    @Test
    void shouldReturnFalseIfPaymentTypeDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(paymentTypeRepository.existsById(id)).thenReturn(false);

        boolean exists = paymentTypeService.isExist(id);

        assertThat(exists).isFalse();
        verify(paymentTypeRepository).existsById(id);
    }
}
