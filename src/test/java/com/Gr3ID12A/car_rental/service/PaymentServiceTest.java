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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
        paymentEntity1 = TestDataUtil.createOnlinePaymentEntity();
        paymentEntity2 = TestDataUtil.createOfflinePaymentEntity();

        paymentDto1 = TestDataUtil.createOnlinePaymentDto();
        paymentDto2 = TestDataUtil.createOfflinePaymentDto();
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


}
