package com.Gr3ID12A.car_rental.services;

import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataDto;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface PersonalDataService {
    List<PersonalDataDto> listPersonalData();

    PersonalDataDto getPersonalData(UUID id);

    PersonalDataDto createPersonalData(PersonalDataRequest personalDataRequest);

    boolean isExist(UUID id);

    PersonalDataDto partialUpdate(UUID id, PersonalDataRequest personalDataRequest);
}
