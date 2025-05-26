package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerDto;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerRequest;
import com.Gr3ID12A.car_rental.domain.entities.CustomerEntity;
import com.Gr3ID12A.car_rental.domain.entities.DiscountEntity;
import com.Gr3ID12A.car_rental.domain.entities.PersonalDataEntity;
import com.Gr3ID12A.car_rental.domain.entities.UserEntity;
import com.Gr3ID12A.car_rental.mappers.CustomerMapper;
import com.Gr3ID12A.car_rental.repositories.CustomerRepository;
import com.Gr3ID12A.car_rental.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public List<CustomerDto> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDto)
                .toList();
    }

    @Override
    public CustomerDto getCustomer(UUID id) {
        Optional<CustomerEntity> customer = customerRepository.findById(id);
        return customer.map(customerEntity -> {
            CustomerDto customerDto = customerMapper.toDto(customerEntity);
            return customerDto;
        }).orElse(null);
    }

    @Override
    public CustomerDto createCustomer(CustomerRequest customerRequest) {
        CustomerEntity customerToSave = customerMapper.toEntity(customerRequest);

        UserEntity user = new UserEntity();
        user.setUuid(customerRequest.getUserId());
        customerToSave.setUser(user);


        PersonalDataEntity personalData = new PersonalDataEntity();
        personalData.setId(customerRequest.getPersonalDataId());
        customerToSave.setPersonalData(personalData);

        Set<DiscountEntity> dicsounts = customerRequest.getDiscountsIds()
                        .stream().map(discount ->{
                            DiscountEntity newDiscount = new DiscountEntity();
                            newDiscount.setId(discount);
                            return newDiscount;
                }).collect(Collectors.toSet());

        customerToSave.setDiscounts(dicsounts);



        CustomerEntity savedCustomer = customerRepository.save(customerToSave);
        return customerMapper.toDto(savedCustomer);
    }

    @Override
    public boolean isExist(UUID id) {
        return customerRepository.existsById(id);
    }

    @Override
    public CustomerDto partialUpdate(UUID id, CustomerRequest customerRequest) {
        CustomerEntity customerToUpdate = customerMapper.toEntity(customerRequest);

        CustomerEntity updatedCustomer = customerRepository.findById(id).map(existingCustomer -> {
            Optional.ofNullable(customerToUpdate.getPersonalData()).ifPresent(existingCustomer::setPersonalData);
            Optional.ofNullable(customerToUpdate.getDiscounts()).ifPresent(existingCustomer::setDiscounts);
            Optional.ofNullable(customerToUpdate.getLoyalty_points()).ifPresent(existingCustomer::setLoyalty_points);
            return customerRepository.save(existingCustomer);
        }).orElseThrow(() -> new EntityNotFoundException("Customer doest not exsit"));

        return customerMapper.toDto(updatedCustomer);
    }

    @Override
    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerEntity getCustomerEntityById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }
}
