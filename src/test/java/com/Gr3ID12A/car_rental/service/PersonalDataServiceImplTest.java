package com.Gr3ID12A.car_rental.service;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataDto;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataRequest;
import com.Gr3ID12A.car_rental.domain.entities.PersonalDataEntity;
import com.Gr3ID12A.car_rental.mappers.PersonalDataMapper;
import com.Gr3ID12A.car_rental.repositories.PersonalDataRepository;
import com.Gr3ID12A.car_rental.services.impl.PersonalDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PersonalDataServiceImplTest {

    @Mock
    private PersonalDataRepository personalDataRepository;

    @Mock
    private PersonalDataMapper personalDataMapper;

    @InjectMocks
    private PersonalDataServiceImpl personalDataService;

    private PersonalDataEntity personalDataEntity;
    private PersonalDataDto personalDataDto;
    private PersonalDataRequest personalDataRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personalDataEntity = TestDataUtil.createTestPersonalDataEntity();
        personalDataDto = TestDataUtil.createTestPersonalDataDtoy();
        personalDataRequest = TestDataUtil.createTestPersonalDataRequest();
    }

    @Test
    void shouldReturnListOfPersonalData() {
        when(personalDataRepository.findAll()).thenReturn(List.of(personalDataEntity));
        when(personalDataMapper.toDto(personalDataEntity)).thenReturn(personalDataDto);

        List<PersonalDataDto> result = personalDataService.listPersonalData();

        assertThat(result).containsExactly(personalDataDto);
    }

    @Test
    void shouldReturnPersonalDataById() {
        UUID id = UUID.randomUUID();

        when(personalDataRepository.findById(id)).thenReturn(Optional.of(personalDataEntity));
        when(personalDataMapper.toDto(personalDataEntity)).thenReturn(personalDataDto);

        PersonalDataDto result = personalDataService.getPersonalData(id);

        assertThat(result).isEqualTo(personalDataDto);
    }

    @Test
    void shouldReturnNullIfPersonalDataNotFound() {
        UUID id = UUID.randomUUID();

        when(personalDataRepository.findById(id)).thenReturn(Optional.empty());

        PersonalDataDto result = personalDataService.getPersonalData(id);

        assertThat(result).isNull();
    }

    @Test
    void shouldCreatePersonalData() {
        when(personalDataMapper.toEntity(personalDataRequest)).thenReturn(personalDataEntity);
        when(personalDataRepository.save(personalDataEntity)).thenReturn(personalDataEntity);
        when(personalDataMapper.toDto(personalDataEntity)).thenReturn(personalDataDto);

        PersonalDataDto result = personalDataService.createPersonalData(personalDataRequest);

        assertThat(result).isEqualTo(personalDataDto);
    }

    @Test
    void shouldCheckIfPersonalDataExists() {
        UUID id = UUID.randomUUID();

        when(personalDataRepository.existsById(id)).thenReturn(true);

        boolean result = personalDataService.isExist(id);

        assertThat(result).isTrue();
    }
}
