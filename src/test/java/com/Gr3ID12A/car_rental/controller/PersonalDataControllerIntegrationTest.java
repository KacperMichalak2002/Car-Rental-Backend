package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.domain.entities.PersonalDataEntity;
import com.Gr3ID12A.car_rental.repositories.AddressRepository;
import com.Gr3ID12A.car_rental.repositories.PersonalDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@WithMockUser
public class PersonalDataControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private AddressRepository addressRepository;
    @Autowired private PersonalDataRepository personalDataRepository;

    private AddressEntity savedAddress;
    private PersonalDataEntity savedPersonalData;

    @BeforeEach
    void setUp() {
        personalDataRepository.deleteAll();
        addressRepository.deleteAll();

        savedAddress = addressRepository.save(TestDataUtil.createTestAddressEntity());
        savedPersonalData = personalDataRepository.save(
                TestDataUtil.createTestPersonalDataEntityWithAddressGivven(savedAddress)
        );
    }

    @Test
    void shouldListAllPersonalData() throws Exception {
        mockMvc.perform(get("/personalData")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].first_name").value("Jan"));
    }

    @Test
    void shouldReturnPersonalDataById() throws Exception {
        mockMvc.perform(get("/personalData/" + savedPersonalData.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("Jan"));
    }

    @Test
    void shouldReturn404WhenPersonalDataNotFound() throws Exception {
        mockMvc.perform(get("/personalData/" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreatePersonalData() throws Exception {
        PersonalDataRequest request = TestDataUtil.createTestPersonalDataRequest(savedAddress.getId());

        mockMvc.perform(post("/personalData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name").value("Jan"));
    }

    @Test
    void shouldReturn404WhenCreatingPersonalDataWithInvalidAddress() throws Exception {
        PersonalDataRequest request = TestDataUtil.createTestPersonalDataRequest(UUID.randomUUID());

        mockMvc.perform(post("/personalData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdatePersonalDataPartially() throws Exception {
        PersonalDataRequest updateRequest = TestDataUtil.createUpdatedPersonalDataRequest(savedAddress.getId());

        mockMvc.perform(patch("/personalData/" + savedPersonalData.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name").value("Adam"));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingPersonalData() throws Exception {
        PersonalDataRequest updateRequest = TestDataUtil.createTestPersonalDataRequest(savedAddress.getId());

        mockMvc.perform(patch("/personalData/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }
}
