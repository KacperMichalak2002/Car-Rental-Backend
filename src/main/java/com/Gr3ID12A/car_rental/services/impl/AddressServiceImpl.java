package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.repositories.AddressRepository;
import com.Gr3ID12A.car_rental.services.AddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public List<AddressEntity> listAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public AddressEntity createAddress(AddressEntity addressToCreate) {
        return addressRepository.save(addressToCreate);
    }

    @Override
    public Optional<AddressEntity> getAddress(UUID id) {
        return addressRepository.findById(id);
    }

    @Override
    public boolean isExist(UUID id) {
        return addressRepository.existsById(id);
    }

    @Override
    public AddressEntity partialUpdate(UUID id, AddressEntity addressToUpdate) {
        return addressRepository.findById(id).map(existingAddress ->{
            Optional.ofNullable(addressToUpdate.getCountry()).ifPresent(existingAddress::setCountry);
            Optional.ofNullable(addressToUpdate.getPostal_code()).ifPresent(existingAddress::setPostal_code);
            Optional.ofNullable(addressToUpdate.getCity()).ifPresent(existingAddress::setCity);
            Optional.ofNullable(addressToUpdate.getStreet()).ifPresent(existingAddress::setStreet);
            Optional.ofNullable(addressToUpdate.getStreet_number()).ifPresent(existingAddress::setStreet_number);
            return addressRepository.save(existingAddress);
        }).orElseThrow(() -> new EntityNotFoundException("Address does not exist"));
    }
}
