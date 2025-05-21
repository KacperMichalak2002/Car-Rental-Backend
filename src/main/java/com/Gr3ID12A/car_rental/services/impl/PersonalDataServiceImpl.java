package com.Gr3ID12A.car_rental.services.impl;

import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataDto;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.domain.entities.PersonalDataEntity;
import com.Gr3ID12A.car_rental.mappers.PersonalDataMapper;
import com.Gr3ID12A.car_rental.repositories.PersonalDataRepository;
import com.Gr3ID12A.car_rental.services.PersonalDataService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonalDataServiceImpl implements PersonalDataService {
    private final PersonalDataRepository personalDataRepository;
    private final PersonalDataMapper personalDataMapper;

    @Override
    public List<PersonalDataDto> listPersonalData() {
        return personalDataRepository.findAll()
                .stream()
                .map(personalDataMapper::toDto)
                .toList();
    }

    @Override
    public PersonalDataDto getPersonalData(UUID id) {
        Optional<PersonalDataEntity> personalDataFound = personalDataRepository.findById(id);
        return personalDataFound.map(personalDataEntity -> {
            PersonalDataDto personalDataDto = personalDataMapper.toDto(personalDataEntity);
            return personalDataDto;
        }).orElse(null);
    }

    @Override
    public PersonalDataDto createPersonalData(PersonalDataRequest personalDataRequest) {
        PersonalDataEntity personalDataToCreate = personalDataMapper.toEntity(personalDataRequest);

        AddressEntity address = new AddressEntity();
        address.setId(personalDataRequest.getAddressId());
        personalDataToCreate.setAddress(address);

        PersonalDataEntity savedPersonalData = personalDataRepository.save(personalDataToCreate);

        return personalDataMapper.toDto(savedPersonalData);
    }

    @Override
    public boolean isExist(UUID id) {
        return personalDataRepository.existsById(id);
    }

    @Override
    public PersonalDataDto partialUpdate(UUID id, PersonalDataRequest personalDataRequest) {
        PersonalDataEntity personalDataToUpdate = personalDataMapper.toEntity(personalDataRequest);

        PersonalDataEntity personalDataUpdated = personalDataRepository.findById(id).map(existingPersonalData ->{
            Optional.ofNullable(personalDataToUpdate.getAddress()).ifPresent(existingPersonalData::setAddress);
            Optional.ofNullable(personalDataToUpdate.getFirst_name()).ifPresent(existingPersonalData::setFirst_name);
            Optional.ofNullable(personalDataToUpdate.getLast_name()).ifPresent(existingPersonalData::setLast_name);
            Optional.ofNullable(personalDataToUpdate.getPesel()).ifPresent(existingPersonalData::setPesel);
            Optional.ofNullable(personalDataToUpdate.getId_number()).ifPresent(existingPersonalData::setId_number);
            Optional.ofNullable(personalDataToUpdate.getPhone_number()).ifPresent(existingPersonalData::setPhone_number);
            Optional.ofNullable(personalDataToUpdate.getEmail()).ifPresent(existingPersonalData::setEmail);
            return personalDataRepository.save(existingPersonalData);
        }).orElseThrow(() -> new EntityNotFoundException("Personal data does not exist"));

        return personalDataMapper.toDto(personalDataUpdated);
    }
}
