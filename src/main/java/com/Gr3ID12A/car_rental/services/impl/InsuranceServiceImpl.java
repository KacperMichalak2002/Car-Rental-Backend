package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.insurance.InsuranceDto;
import com.Gr3ID12A.car_rental.domain.dto.insurance.InsuranceRequest;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;
import com.Gr3ID12A.car_rental.domain.entities.InsuranceEntity;
import com.Gr3ID12A.car_rental.domain.entities.RentalEntity;
import com.Gr3ID12A.car_rental.mappers.InsuranceMapper;
import com.Gr3ID12A.car_rental.repositories.InsuranceRepository;
import com.Gr3ID12A.car_rental.services.InsuranceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InsuranceServiceImpl implements InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final InsuranceMapper insuranceMapper;

    @Override
    public List<InsuranceDto> listInsurances() {
        return insuranceRepository.findAll().stream()
                .map(insuranceMapper::toDto)
                .toList();
    }

    @Override
    public InsuranceDto getInsuranceByRentalId(UUID id) {
        Optional<InsuranceEntity> insurance = insuranceRepository.findByRentalId(id);
        return insurance.map(insuranceEntity -> {
            InsuranceDto insuranceDto = insuranceMapper.toDto(insuranceEntity);
            return insuranceDto;
        }).orElse(null);
    }

    @Override
    public InsuranceDto createInsurance(InsuranceRequest insuranceRequest) {

        InsuranceEntity insuranceToSave = insuranceMapper.toEntity(insuranceRequest);

        RentalEntity rental = new RentalEntity();
        rental.setId(insuranceRequest.getRentalId());

        insuranceToSave.setRental(rental);

        InsuranceEntity savedInsurance = insuranceRepository.save(insuranceToSave);

        return insuranceMapper.toDto(savedInsurance);
    }


}
