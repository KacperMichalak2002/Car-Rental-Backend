package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.customerDiscounts.CustomerDiscountsDto;
import com.Gr3ID12A.car_rental.domain.dto.customerDiscounts.CustomerDiscountsRequest;
import com.Gr3ID12A.car_rental.services.CustomerDiscountsService;
import com.Gr3ID12A.car_rental.services.CustomerService;
import com.Gr3ID12A.car_rental.services.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customerDiscounts")
@RequiredArgsConstructor
public class CustomerDiscountsController {

    private final CustomerDiscountsService customerDiscountsService;
    private final CustomerService customerService;
    private final DiscountService discountService;

    @GetMapping
    public ResponseEntity<List<CustomerDiscountsDto>> listCustomersDiscounts(){
        List<CustomerDiscountsDto> list = customerDiscountsService.listCustomerDiscounts();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }




    @PostMapping
    public ResponseEntity<CustomerDiscountsDto> assignDiscountToCustomer(@RequestBody CustomerDiscountsRequest customerDiscountsRequest){
        boolean customerExist = customerService.isExist(customerDiscountsRequest.getCustomerId());
        boolean discountExist = discountService.isExist(customerDiscountsRequest.getDiscountId());

        if(!customerExist || !discountExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerDiscountsDto assignedDiscount = customerDiscountsService.assignDiscount(customerDiscountsRequest);
        return new ResponseEntity<>(assignedDiscount, HttpStatus.CREATED);
    }

}
