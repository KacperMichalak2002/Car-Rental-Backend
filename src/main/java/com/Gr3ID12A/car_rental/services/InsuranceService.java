package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.insurance.InsuranceDto;
import com.Gr3ID12A.car_rental.domain.dto.insurance.InsuranceRequest;
import com.Gr3ID12A.car_rental.domain.dto.rental.RentalDto;

import java.util.List;
import java.util.UUID;

public interface InsuranceService {
    List<InsuranceDto> listInsurances();

    InsuranceDto getInsuranceByRentalId(UUID id);

    InsuranceDto createInsurance(InsuranceRequest insuranceRequest);
}
