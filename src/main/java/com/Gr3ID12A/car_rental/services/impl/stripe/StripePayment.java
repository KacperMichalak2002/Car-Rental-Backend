package com.Gr3ID12A.car_rental.services.impl.stripe;

import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentDto;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentRequest;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentStatus;
import com.Gr3ID12A.car_rental.domain.dto.payment.stripe.StripeResponse;
import com.Gr3ID12A.car_rental.domain.entities.PaymentEntity;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentName;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentTypeEntity;
import com.Gr3ID12A.car_rental.domain.entities.RentalEntity;
import com.Gr3ID12A.car_rental.mappers.PaymentMapper;
import com.Gr3ID12A.car_rental.repositories.PaymentRepository;
import com.Gr3ID12A.car_rental.repositories.PaymentTypeRepository;
import com.Gr3ID12A.car_rental.services.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripePayment implements PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentTypeRepository paymentTypeRepository;


    @Value("${STRIPE_SECRET}")
    String STRIPE_API;

    @Value("${STRIPE_WEBHOOK_SECRET}")
    String WEBHOOK_KEY;

    @Value("${DOMAIN}")
    String Domain;

    @Override
    public List<PaymentDto> listPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toDto)
                .toList();
    }

    public String getPriceId(PaymentRequest paymentRequest){
        try{
            BigDecimal price = paymentRequest.getCost();
            long unitAmount = price
                    .multiply(BigDecimal.valueOf(100))
                    .setScale(0, RoundingMode.HALF_UP)
                    .longValue();

            PriceCreateParams params =
                    PriceCreateParams.builder()
                            .setCurrency("PLN")
                            .setUnitAmount(unitAmount)
                            .setProductData(
                                    PriceCreateParams.ProductData.builder().setName(paymentRequest.getTitle()).build()
                            )
                            .build();
            return Price.create(params).getId();
        }catch (StripeException e){
            log.error("Error while creating stripe price: {}", e.getMessage());
            throw new RuntimeException("Error while creating stripe price", e);
        }

    }

    @Override
    public StripeResponse createPayment(PaymentRequest paymentRequest) {

        Stripe.apiKey = STRIPE_API;


        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(Domain + "success") // set forwarding after success and cancel
                        .setCancelUrl(Domain + "cancel")
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.BLIK)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPrice(getPriceId(paymentRequest))
                                        .build()
                        )
                        .build();
        try{
            Session session = Session.create(params);

            PaymentEntity paymentToSave = paymentMapper.toEntity(paymentRequest);

            RentalEntity rental = new RentalEntity();
            rental.setId(paymentRequest.getRentalId());
            paymentToSave.setRental(rental);

            PaymentTypeEntity paymentType = paymentTypeRepository.findByName(PaymentName.ONLINE).orElse(null);
            paymentToSave.setPayment_type(paymentType);

            paymentToSave.setSessionId(session.getId());
            paymentToSave.setStatus(PaymentStatus.PENDING.toString());
            paymentRepository.save(paymentToSave);

            return StripeResponse.builder()
                    .status("SUCCESS")
                    .message("Payment session created")
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .build();

        }catch (StripeException e){
            log.error("Error creating checkout session in stripe {}", e.getMessage());

            PaymentEntity failedPayment = paymentMapper.toEntity(paymentRequest);
            failedPayment.setStatus(PaymentStatus.FAILED.toString());
            failedPayment.setDate_of_payment(LocalDate.now());
            paymentRepository.save(failedPayment);

            throw new RuntimeException("Error creating checkout session in stripe", e);
        }

    }


    @Override
    public boolean isExist(UUID id) {
        return paymentRepository.existsById(id);
    }

    @Override
    public PaymentDto getPaymentById(UUID id) {
        Optional<PaymentEntity> payment = paymentRepository.findById(id);
        return payment.map(paymentEntity -> {
            PaymentDto paymentDto = paymentMapper.toDto(paymentEntity);
            return paymentDto;
        }).orElse(null);
    }

    @Override
    public PaymentDto partialUpdatePayment(UUID id, PaymentRequest paymentRequest) {
        PaymentEntity paymentToUpdate = paymentMapper.toEntity(paymentRequest);

        PaymentEntity updatedPayment = paymentRepository.findById(id).map(existingPayment ->{
            Optional.ofNullable(paymentToUpdate.getStatus()).ifPresent(existingPayment::setStatus);
            return paymentRepository.save(existingPayment);
        }).orElseThrow(() -> new EntityNotFoundException("Payment now found"));

        return paymentMapper.toDto(updatedPayment);
    }

    @Override
    public String handleWebhook(String payload, String sigHeader) {
        try{
            Event event = Webhook.constructEvent(payload,sigHeader,WEBHOOK_KEY);

            switch (event.getType()){
                case "checkout.session.completed":
                case "checkout.session.async_payment_succeeded":
                case "payment_intent.succeeded":
                    handlePaymentSuccess(event);
                    return "Completed";

                case "checkout.session.expired":
                case "payment_intent.payment_failed":
                    handlePaymentFailed(event);
                    return "Failed";

                default:
                    return "Failed";

            }

        }catch (Exception e){
            log.error("Stripe webhook error: {}", e.getMessage());
            return "Webhook error";
        }

    }

    private void handlePaymentSuccess(Event event){
        Session session = (Session) event.getDataObjectDeserializer().getObject().get();
        String sessionId = session.getId();

        PaymentEntity payment = paymentRepository.findBySessionId(sessionId).orElseThrow(() -> new RuntimeException("payment not found"));
        payment.setStatus(PaymentStatus.COMPLETED.toString());
        payment.setDate_of_payment(LocalDate.now());

        paymentRepository.save(payment);
    }

    private void handlePaymentFailed(Event event){
        Session session = (Session) event.getDataObjectDeserializer().getObject().get();
        String sessionId = session.getId();

        PaymentEntity payment = paymentRepository.findBySessionId(sessionId).orElseThrow(() -> new RuntimeException("payment not found"));
        payment.setStatus(PaymentStatus.FAILED.toString());
        payment.setDate_of_payment(LocalDate.now());

        paymentRepository.save(payment);

    }

    @Override
    public PaymentDto confirmOfflinePayment(UUID id) {
        PaymentEntity payment = paymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.COMPLETED.name());

        PaymentTypeEntity paymentType = paymentTypeRepository.findByName(PaymentName.OFFLINE).orElse(null);
        payment.setPayment_type(paymentType);
        payment.setDate_of_payment(LocalDate.now());
        paymentRepository.save(payment);

        return paymentMapper.toDto(payment);
    }
}
