package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentDto;
import com.Gr3ID12A.car_rental.domain.entities.PaymentEntity;
import com.Gr3ID12A.car_rental.mappers.PaymentMapper;
import com.Gr3ID12A.car_rental.repositories.PaymentRepository;
import com.Gr3ID12A.car_rental.repositories.PaymentTypeRepository;
import com.Gr3ID12A.car_rental.services.impl.stripe.StripePayment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private PaymentTypeRepository paymentTypeRepository;

    @InjectMocks
    private StripePayment stripePayment;


    private PaymentEntity paymentEntity1;
    private PaymentEntity paymentEntity2;
    private PaymentDto paymentDto1;
    private PaymentDto paymentDto2;


    @BeforeEach
    void setUp(){
        paymentEntity1 = TestDataUtil.createTestOnlinePaymentEntity();
        paymentEntity2 = TestDataUtil.createTestOfflinePaymentEntity();

        paymentDto1 = TestDataUtil.createTestOnlinePaymentDto();
        paymentDto2 = TestDataUtil.createTestOfflinePaymentDto();
    }

    @Test
    void testThatListPaymentReturnsPayments(){

        List<PaymentEntity> paymentEntities = List.of(paymentEntity1, paymentEntity2);

        when(paymentRepository.findAll()).thenReturn(paymentEntities);
        when(paymentMapper.toDto(paymentEntity1)).thenReturn(paymentDto1);
        when(paymentMapper.toDto(paymentEntity2)).thenReturn(paymentDto2);

        List<PaymentDto> result = stripePayment.listPayments();

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(paymentDto1,paymentDto2);

    }

    @Test
    void testThatPaymentIsReturnedById(){

        PaymentEntity payment = paymentEntity1;

        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));
        when(paymentMapper.toDto(paymentEntity1)).thenReturn(paymentDto1);

        PaymentDto result = stripePayment.getPaymentById(payment.getId());

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(paymentDto1);
    }

    @Test
    void testThatPaymentIsNotFound(){
        UUID id = UUID.randomUUID();

        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        PaymentDto result = stripePayment.getPaymentById(id);

        assertThat(result).isNull();
    }

    @Test
    void testThatPartialUpdateIsSuccessful(){
        PaymentEntity payment = paymentEntity1;
    }





}
