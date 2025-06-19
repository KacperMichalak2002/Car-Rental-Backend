package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentDto;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentRequest;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentStatus;
import com.Gr3ID12A.car_rental.domain.dto.payment.stripe.StripeResponse;
import com.Gr3ID12A.car_rental.domain.entities.PaymentEntity;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentName;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentTypeEntity;
import com.Gr3ID12A.car_rental.mappers.PaymentMapper;
import com.Gr3ID12A.car_rental.repositories.PaymentRepository;
import com.Gr3ID12A.car_rental.repositories.PaymentTypeRepository;
import com.Gr3ID12A.car_rental.services.impl.stripe.StripePayment;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

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
        UUID id = payment.getId();

        PaymentRequest paymentRequest = TestDataUtil.createTestPaymentRequestOnline();
        PaymentDto expectedPayment = new PaymentDto();
        expectedPayment.setId(id);
        expectedPayment.setStatus(PaymentStatus.FAILED.name());

        when(paymentMapper.toEntity(paymentRequest)).thenReturn(payment);
        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(paymentMapper.toDto(payment)).thenReturn(expectedPayment);


        PaymentDto result = stripePayment.partialUpdatePayment(id, paymentRequest);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(PaymentStatus.FAILED.name());
        assertThat(result.getId()).isEqualTo(id);

    }

    @Test
    void testThatExceptionThrownWhenPaymentNotFound(){
        UUID id = UUID.randomUUID();
        PaymentRequest paymentRequest = TestDataUtil.createTestPaymentRequestOnline();

        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> stripePayment.partialUpdatePayment(id, paymentRequest))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Payment not found");
    }

    @Test
    void testThatCreatePaymentIsSuccessful(){

        PaymentRequest paymentRequest = TestDataUtil.createTestPaymentRequestOnline();
        PaymentEntity payment = new PaymentEntity();
        PaymentTypeEntity paymentType = new PaymentTypeEntity();
        paymentType.setName(PaymentName.ONLINE);

        Session mockSession = mock(Session.class);
        Price mockPrice = mock(Price.class);

        when(mockSession.getId()).thenReturn("cs_test_sessionId");
        when(mockSession.getUrl()).thenReturn("https://stripe.checkout.com/test_url");
        when(mockPrice.getId()).thenReturn("price_id_test");

        when(paymentMapper.toEntity(paymentRequest)).thenReturn(payment);
        when(paymentTypeRepository.findByName(PaymentName.ONLINE)).thenReturn(Optional.of(paymentType));
        when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(payment);

        try(MockedStatic<Price> mockedPrice = mockStatic(Price.class);
            MockedStatic<Session> mockedSession = mockStatic(Session.class)){

            mockedPrice.when(()-> Price.create(any(PriceCreateParams.class))).thenReturn(mockPrice);
            mockedSession.when(() -> Session.create(any(SessionCreateParams.class))).thenReturn(mockSession);

            StripeResponse result = stripePayment.createPayment(paymentRequest);

            assertThat(result).isNotNull();
            assertThat(result.getStatus()).isEqualTo("SUCCESS");
            assertThat(result.getMessage()).isEqualTo("Payment session created");
            assertThat(result.getSessionId()).isEqualTo("cs_test_sessionId");
            assertThat(result.getSessionUrl()).isEqualTo("https://stripe.checkout.com/test_url");

        }

    }

    @Test
    void testThatCreatePaymentOfflineIsSuccessful(){

        PaymentRequest paymentRequest = TestDataUtil.createTestPaymentRequestOffline();
        PaymentEntity paymentToBeSaved = new PaymentEntity();
        PaymentTypeEntity paymentTypeEntity = new PaymentTypeEntity();
        paymentTypeEntity.setName(PaymentName.OFFLINE);

        when(paymentMapper.toEntity(paymentRequest)).thenReturn(paymentToBeSaved);
        when(paymentTypeRepository.findByName(PaymentName.OFFLINE)).thenReturn(Optional.of(paymentTypeEntity));
        when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(paymentToBeSaved);

        StripeResponse result = stripePayment.createPayment(paymentRequest);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("SUCCESS");
        assertThat(result.getMessage()).isEqualTo("Offline payment created");
        assertThat(result.getSessionId()).isNull();
        assertThat(result.getSessionUrl()).isNull();
    }

    @Test
    void testCreatingPriceId(){
        PaymentRequest paymentRequest = TestDataUtil.createTestPaymentRequestOnline();
        Price mockPrice = mock(Price.class);

        when(mockPrice.getId()).thenReturn("price_test_id");

        try(MockedStatic<Price> mockedPrice = mockStatic(Price.class)){
            mockedPrice.when( () -> Price.create(any(PriceCreateParams.class))).thenReturn(mockPrice);

            String result = stripePayment.getPriceId(paymentRequest);

            assertThat(result).isNotNull();
            assertThat(result).isEqualTo("price_test_id");
        }

    }

    @Test
    void testThatCreatingPriceIdThrowsException(){
        PaymentRequest paymentRequest = TestDataUtil.createTestPaymentRequestOnline();

        try(MockedStatic<Price> mockedPrice = mockStatic(Price.class)){
            mockedPrice.when( () -> Price.create(any(PriceCreateParams.class)))
                    .thenThrow(new StripeException("Stripe API error","req_123","ivalid_request",400){});

            assertThatThrownBy(() -> stripePayment.getPriceId(paymentRequest))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Error while creating stripe price")
                    .hasCauseInstanceOf(StripeException.class);
        }
    }

    @Test
    void testThatIsExistReturnsTrueWhenPaymentExist(){
        UUID id = TestDataUtil.createTestOnlinePaymentEntity().getId();

        when(paymentRepository.existsById(id)).thenReturn(true);

        boolean result = stripePayment.isExist(id);

        assertThat(result).isTrue();
    }

    @Test
    void testThatIsExistReturnsFalseWhenPaymentDoesNotExist(){
        UUID id = UUID.randomUUID();

        when(paymentRepository.existsById(id)).thenReturn(false);

        boolean result = stripePayment.isExist(id);

        assertThat(result).isFalse();
    }
}
