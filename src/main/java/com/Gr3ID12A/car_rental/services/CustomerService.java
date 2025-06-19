package com.Gr3ID12A.car_rental.services;


import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerDto;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerRequest;
import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerDto> listCustomers();

    CustomerDto getCustomer(UUID id);

    CustomerDto createCustomer(CustomerRequest customerRequest);

    boolean isExist(UUID id);

    CustomerDto partialUpdate(UUID id, CustomerRequest customerRequest);

    void deleteCustomer(UUID id);

    CustomerDto getCustomerByUserId(UUID id);
}
