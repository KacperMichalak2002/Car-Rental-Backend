package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.make.MakeDto;
import com.Gr3ID12A.car_rental.domain.entities.MakeEntity;
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

    @Override
    public List<MakeEntity> listMakes() {
        return makeRepository.findAll();
    }

    @Override
    public MakeEntity createMake(MakeEntity makeToCreate) {
        return makeRepository.save(makeToCreate);
    }

    @Override
    public void delete(UUID id) {
        makeRepository.deleteById(id);
    }
}
