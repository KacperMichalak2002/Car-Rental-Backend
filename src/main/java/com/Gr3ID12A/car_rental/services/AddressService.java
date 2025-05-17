package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.address.AddressDto;
import com.Gr3ID12A.car_rental.domain.dto.address.AddressRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressService {
    List<AddressDto> listAddresses();

    AddressDto createAddress(AddressRequest addressRequest);

    AddressDto getAddress(UUID id);

    boolean isExist(UUID id);

    AddressDto partialUpdate(UUID id, AddressRequest addressRequest);

    AddressEntity getAddressEntityById(UUID id);
}
