package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.customerDiscounts.CustomerDiscountsDto;
import com.Gr3ID12A.car_rental.domain.dto.customerDiscounts.CustomerDiscountsRequest;

import java.util.List;

public interface CustomerDiscountsService {
    CustomerDiscountsDto assignDiscount(CustomerDiscountsRequest customerDiscountsRequest);

    List<CustomerDiscountsDto> listCustomerDiscounts();
}
