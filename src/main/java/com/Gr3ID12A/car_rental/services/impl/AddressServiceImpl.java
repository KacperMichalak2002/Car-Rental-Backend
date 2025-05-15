package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.address.AddressDto;
import com.Gr3ID12A.car_rental.domain.dto.address.AddressRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.mappers.AddressMapper;
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
    private final AddressMapper addressMapper;

    @Override
    public List<AddressDto> listAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(addressMapper::toDto)
                .toList();
    }

    @Override
    public AddressDto createAddress(AddressRequest addressRequest) {
        AddressEntity savedAddress = addressRepository.save(addressMapper.toEntity(addressRequest));
        return addressMapper.toDto(savedAddress);
    }

    @Override
    public AddressDto getAddress(UUID id) {
        Optional<AddressEntity> receivedAddress = addressRepository.findById(id);

        return receivedAddress.map(addressEntity -> {
            AddressDto addressDto = addressMapper.toDto(addressEntity);
            return addressDto;

        }).orElse(null);
    }

    @Override
    public boolean isExist(UUID id) {
        return addressRepository.existsById(id);
    }

    @Override
    public AddressDto partialUpdate(UUID id, AddressRequest addressRequest) {
        AddressEntity addressToUpdate = addressMapper.toEntity(addressRequest);

        AddressEntity updatedAddress = addressRepository.findById(id).map(existingAddress ->{
            Optional.ofNullable(addressToUpdate.getCountry()).ifPresent(existingAddress::setCountry);
            Optional.ofNullable(addressToUpdate.getPostal_code()).ifPresent(existingAddress::setPostal_code);
            Optional.ofNullable(addressToUpdate.getCity()).ifPresent(existingAddress::setCity);
            Optional.ofNullable(addressToUpdate.getStreet()).ifPresent(existingAddress::setStreet);
            Optional.ofNullable(addressToUpdate.getStreet_number()).ifPresent(existingAddress::setStreet_number);
            return addressRepository.save(existingAddress);
        }).orElseThrow(() -> new EntityNotFoundException("Address does not exist"));

        return addressMapper.toDto(updatedAddress);
    }
}
