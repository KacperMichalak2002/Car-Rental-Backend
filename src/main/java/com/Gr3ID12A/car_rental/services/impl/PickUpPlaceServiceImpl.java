package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.address.AddressDto;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceDto;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.domain.entities.PickUpPlaceEntity;
import com.Gr3ID12A.car_rental.mappers.PickUpPlaceMapper;
import com.Gr3ID12A.car_rental.repositories.AddressRepository;
import com.Gr3ID12A.car_rental.repositories.PickUpPlaceRepository;
import com.Gr3ID12A.car_rental.services.AddressService;
import com.Gr3ID12A.car_rental.services.PickUpPlaceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PickUpPlaceServiceImpl implements PickUpPlaceService {
    private final PickUpPlaceRepository pickUpPlaceRepository;
    private final PickUpPlaceMapper pickUpPlaceMapper;
    @Override
    public List<PickUpPlaceDto> listPickUpPlaces() {
        return pickUpPlaceRepository.findAll()
                .stream()
                .map(pickUpPlaceMapper::toDto)
                .toList();
    }

    @Override
    public PickUpPlaceDto createPickUpPlace(PickUpPlaceRequest pickUpPlaceRequest) {
            PickUpPlaceEntity pickUpPlaceToCreate = pickUpPlaceMapper.toEntity(pickUpPlaceRequest);
            PickUpPlaceEntity pickUpPlaceSaved = pickUpPlaceRepository.save(pickUpPlaceToCreate);
            return  pickUpPlaceMapper.toDto(pickUpPlaceSaved);
    }
}
