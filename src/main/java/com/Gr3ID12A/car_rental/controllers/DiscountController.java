package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.discount.DiscountDto;
import com.Gr3ID12A.car_rental.domain.dto.discount.DiscountRequest;
import com.Gr3ID12A.car_rental.services.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public ResponseEntity<List<DiscountDto>> listDiscounts(){
        List<DiscountDto> discounts = discountService.listDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DiscountDto> getDiscountById(@Valid @PathVariable("id") UUID id){
        DiscountDto discount = discountService.getDiscount(id);
        if(discount == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(discount, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<DiscountDto> createDiscount(@Valid @RequestBody DiscountRequest discountRequest){
        DiscountDto discountSaved = discountService.createDiscount(discountRequest);
        return new ResponseEntity<>(discountSaved, HttpStatus.CREATED);
    }



}
