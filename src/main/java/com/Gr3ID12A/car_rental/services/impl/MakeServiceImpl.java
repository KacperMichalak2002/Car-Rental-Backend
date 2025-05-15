package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.make.MakeDto;
import com.Gr3ID12A.car_rental.domain.dto.make.MakeRequest;
import com.Gr3ID12A.car_rental.domain.entities.MakeEntity;
import com.Gr3ID12A.car_rental.mappers.MakeMapper;
import com.Gr3ID12A.car_rental.repositories.MakeRepository;
import com.Gr3ID12A.car_rental.services.MakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MakeServiceImpl implements MakeService {

    private final MakeRepository makeRepository;
    private final MakeMapper makeMapper;

    @Override
    public List<MakeDto> listMakes() {
        return makeRepository.findAll()
                .stream()
                .map(makeMapper::toDto)
                .toList();
    }

    @Override
    public MakeDto createMake(MakeRequest makeRequest) {
        MakeEntity makeToCreate = makeMapper.toEntity(makeRequest);
        MakeEntity savedMake = makeRepository.save(makeToCreate);
        return makeMapper.toDto(savedMake);
    }

    @Override
    public void delete(UUID id) {
        makeRepository.deleteById(id);
    }
}
