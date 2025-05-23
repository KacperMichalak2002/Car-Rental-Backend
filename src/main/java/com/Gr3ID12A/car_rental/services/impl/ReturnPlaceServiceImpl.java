package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.domain.entities.ReturnPlaceEntity;
import com.Gr3ID12A.car_rental.mappers.ReturnPlaceMapper;
import com.Gr3ID12A.car_rental.repositories.ReturnPlaceRepository;
import com.Gr3ID12A.car_rental.services.AddressService;
import com.Gr3ID12A.car_rental.services.ReturnPlaceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReturnPlaceServiceImpl implements ReturnPlaceService {
    private final ReturnPlaceRepository returnPlaceRepository;
    private final ReturnPlaceMapper returnPlaceMapper;
    private final AddressService addressService;

    @Override
    public List<ReturnPlaceDto> listReturnPlaces() {
        return returnPlaceRepository.findAll()
                .stream()
                .map(returnPlaceMapper::toDto)
                .toList();
    }

    @Override
    public ReturnPlaceDto createReturnPlace(ReturnPlaceRequest returnPlaceRequest) {
        ReturnPlaceEntity returnPlaceEntityToCreate = returnPlaceMapper.toEntity(returnPlaceRequest);

        AddressEntity address = new AddressEntity();
        address.setId(returnPlaceRequest.getAddressId());

        returnPlaceEntityToCreate.setAddress(address);
        ReturnPlaceEntity savedReturnPlace = returnPlaceRepository.save(returnPlaceEntityToCreate);

        return returnPlaceMapper.toDto(savedReturnPlace);
    }

    @Override
    public ReturnPlaceEntity getReturnPlaceEntityById(UUID returnPlaceId) {
        return returnPlaceRepository.findById(returnPlaceId).orElseThrow(() -> new EntityNotFoundException("Return place not found"));
    }

    @Override
    public boolean isExist(UUID returnPlaceId) {
        return returnPlaceRepository.existsById(returnPlaceId);
    }
}
