package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.returnPlace.ReturnPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.domain.entities.ReturnPlaceEntity;
import com.Gr3ID12A.car_rental.mappers.ReturnPlaceMapper;
import com.Gr3ID12A.car_rental.repositories.ReturnPlaceRepository;
import com.Gr3ID12A.car_rental.services.AddressService;
import com.Gr3ID12A.car_rental.services.ReturnPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        AddressEntity address = addressService.getAddressEntityById(returnPlaceRequest.getAddressId());
        ReturnPlaceEntity returnPlaceEntityToCreate = returnPlaceMapper.toEntity(returnPlaceRequest);
        returnPlaceEntityToCreate.setAddress(address);
        ReturnPlaceEntity savedReturnPlace = returnPlaceRepository.save(returnPlaceEntityToCreate);
        return returnPlaceMapper.toDto(savedReturnPlace);
    }
}
