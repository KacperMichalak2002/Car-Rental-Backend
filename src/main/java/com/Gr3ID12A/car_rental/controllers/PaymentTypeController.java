package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeDto;
import com.Gr3ID12A.car_rental.domain.dto.paymentType.PaymentTypeRequest;
import com.Gr3ID12A.car_rental.services.PaymentTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/paymentTypes")
@RequiredArgsConstructor
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    @GetMapping
    public ResponseEntity<List<PaymentTypeDto>> listPaymentTypes(){
       List<PaymentTypeDto> paymentTypes = paymentTypeService.listPaymentType();
       return ResponseEntity.ok(paymentTypes);
    }

    @PostMapping
    public ResponseEntity<PaymentTypeDto> createPaymentType(@Valid @RequestBody PaymentTypeRequest paymentTypeRequest){
        PaymentTypeDto paymentTypeSaved = paymentTypeService.createPaymentType(paymentTypeRequest);
        return new ResponseEntity<>(paymentTypeSaved, HttpStatus.CREATED);
    }

}
