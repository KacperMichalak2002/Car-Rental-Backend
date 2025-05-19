package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.customerDiscounts.CustomerDiscountsDto;
import com.Gr3ID12A.car_rental.domain.dto.customerDiscounts.CustomerDiscountsRequest;
import com.Gr3ID12A.car_rental.domain.entities.CustomerDiscountsEntity;
import com.Gr3ID12A.car_rental.domain.entities.CustomerEntity;
import com.Gr3ID12A.car_rental.domain.entities.DiscountEntity;
import com.Gr3ID12A.car_rental.mappers.CustomerDiscountsMapper;
import com.Gr3ID12A.car_rental.repositories.CustomerDiscountsRepository;
import com.Gr3ID12A.car_rental.services.CustomerDiscountsService;
import com.Gr3ID12A.car_rental.services.CustomerService;
import com.Gr3ID12A.car_rental.services.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDiscountsServiceImpl implements CustomerDiscountsService {

    private final CustomerDiscountsMapper customerDiscountsMapper;
    private final CustomerDiscountsRepository customerDiscountsRepository;
    private final CustomerService customerService;
    private final DiscountService discountService;

    @Override
    public CustomerDiscountsDto assignDiscount(CustomerDiscountsRequest customerDiscountsRequest) {
        CustomerDiscountsEntity customerDiscountsToCreate = customerDiscountsMapper.toEntity(customerDiscountsRequest);
        CustomerEntity customer = customerService.getCustomerEntityById(customerDiscountsRequest.getCustomerId());
        DiscountEntity discount = discountService.getDiscountEntityById(customerDiscountsRequest.getDiscountId());

        customerDiscountsToCreate.setCustomer(customer);
        customerDiscountsToCreate.setDiscount(discount);

        CustomerDiscountsEntity customerDiscountsSaved = customerDiscountsRepository.save(customerDiscountsToCreate);

        return customerDiscountsMapper.toDto(customerDiscountsSaved);
    }
}
