package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerDto;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerRequest;
import com.Gr3ID12A.car_rental.services.CustomerService;
import com.Gr3ID12A.car_rental.services.PersonalDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final PersonalDataService personalDataService;

    @GetMapping
    public ResponseEntity<List<CustomerDto>> listCustomers(){
        List<CustomerDto> customers = customerService.listCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("id")UUID id){
        CustomerDto customer = customerService.getCustomer(id);
        if(customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerRequest customerRequest){
        boolean personalDataExist = personalDataService.isExist(customerRequest.getPersonalDataId());
        if(!personalDataExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CustomerDto savedCustomer = customerService.createCustomer(customerRequest);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CustomerDto> partialUpdateCustomer(@PathVariable("id") UUID id ,@RequestBody CustomerRequest customerRequest){
        boolean customerExist = customerService.isExist(id);
        if(!customerExist){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerDto updatedCustomer = customerService.partialUpdate(id, customerRequest);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") UUID id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
