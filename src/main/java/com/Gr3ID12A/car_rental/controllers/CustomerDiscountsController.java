package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.customerDiscounts.CustomerDiscountsDto;
import com.Gr3ID12A.car_rental.domain.dto.customerDiscounts.CustomerDiscountsRequest;
import com.Gr3ID12A.car_rental.services.CustomerDiscountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/customerDiscounts")
@RequiredArgsConstructor
public class CustomerDiscountsController {

    private final CustomerDiscountsService customerDiscountsService;

    @PostMapping
    public ResponseEntity<CustomerDiscountsDto> assignDiscountToCustomer(@RequestBody CustomerDiscountsRequest customerDiscountsRequest){
        CustomerDiscountsDto assignedDiscount = customerDiscountsService.assignDiscount(customerDiscountsRequest);
        return new ResponseEntity<>(assignedDiscount, HttpStatus.CREATED);
    }

}
