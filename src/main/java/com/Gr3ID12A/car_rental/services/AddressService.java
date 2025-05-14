package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressService {
    List<AddressEntity> listAddresses();

    AddressEntity createAddress(AddressEntity addressToCreate);

    Optional<AddressEntity> getAddress(UUID id);

    boolean isExist(UUID id);

    AddressEntity partialUpdate(UUID id, AddressEntity addressToUpdate);
}
