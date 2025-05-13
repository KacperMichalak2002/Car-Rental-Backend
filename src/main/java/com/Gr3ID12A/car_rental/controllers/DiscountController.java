package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.discount.DiscountDto;
import com.Gr3ID12A.car_rental.domain.dto.discount.DiscountRequest;
import com.Gr3ID12A.car_rental.domain.entities.DiscountEntity;
import com.Gr3ID12A.car_rental.mappers.DiscountMapper;
import com.Gr3ID12A.car_rental.services.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;
    private final DiscountMapper discountMapper;

    @GetMapping
    public ResponseEntity<List<DiscountDto>> listDiscounts(){
        List<DiscountDto> discounts = discountService.listDiscounts()
                .stream()
                .map(discountMapper::toDto)
                .toList();
        return ResponseEntity.ok(discounts);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DiscountDto> getDiscountById(@Valid @PathVariable("id") UUID id){
        Optional<DiscountEntity> discount = discountService.getDiscount(id);
        return discount.map(discountEntity -> {
            DiscountDto discountDto = discountMapper.toDto(discountEntity);
            return new ResponseEntity<>(discountDto, HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PostMapping
    public ResponseEntity<DiscountDto> createDiscount(@Valid @RequestBody DiscountRequest discountRequest){
        DiscountEntity discountToCreate = discountMapper.toEntity(discountRequest);
        DiscountEntity discountSaved = discountService.createDiscount(discountToCreate);
        return new ResponseEntity<>(discountMapper.toDto(discountSaved), HttpStatus.CREATED);
    }



}
