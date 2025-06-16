package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentDto;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentRequest;
import com.Gr3ID12A.car_rental.domain.dto.payment.stripe.StripeResponse;
import com.Gr3ID12A.car_rental.services.PaymentService;
import com.Gr3ID12A.car_rental.services.PaymentTypeService;
import com.Gr3ID12A.car_rental.services.RentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(path = "/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final RentalService rentalService;
    private final PaymentTypeService paymentTypeService;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> listPayments(){
        List<PaymentDto> payments = paymentService.listPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable("id")UUID id){
        PaymentDto returnedPayment = paymentService.getPaymentById(id);
        if(returnedPayment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(returnedPayment, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<StripeResponse> createPayment(@RequestBody PaymentRequest paymentRequest ){

        boolean rentalExist = rentalService.isExist(paymentRequest.getRentalId());
        boolean paymentTypeExist = paymentTypeService.isExist(paymentRequest.getPaymentTypeId());

       if(!rentalExist || !paymentTypeExist){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

        StripeResponse createdPayment = paymentService.createPayment(paymentRequest);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);

    }

    @PostMapping("/webhook/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader){

        try{
            if(sigHeader == null || sigHeader.isEmpty()){
                log.error("Missing Stripe signature");
                return new ResponseEntity<>("Missing signature", HttpStatus.BAD_REQUEST);
            }

            String status = paymentService.handleWebhook(payload,sigHeader);

            return new ResponseEntity<>(status,HttpStatus.OK);

        }catch (Exception e){
            log.error("Error processing Stripe webhook {}", e.getMessage());
            return new ResponseEntity<>("Webhook processing failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(path = "/{id}/status")
    public ResponseEntity<PaymentDto> partialUpdatePaymentStatus(@PathVariable("id") UUID id, @RequestBody PaymentRequest paymentRequest){
        boolean paymentExist = paymentService.isExist(id);
        if(!paymentExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PaymentDto updatedPayment = paymentService.partialUpdatePayment(id, paymentRequest);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);


    }
}
