package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.personalData.PersonalDataRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.repositories.*;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@WithMockUser
public class PersonalDataControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private PersonalDataRepository personalDataRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private PickUpPlaceRepository pickUpPlaceRepository;
    @Autowired private ReturnPlaceRepository returnPlaceRepository;
    @Autowired private RentalRepository rentalRepository;
    @Autowired private PaymentRepository paymentRepository;

    private AddressEntity savedAddress;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();
        rentalRepository.deleteAll();
        pickUpPlaceRepository.deleteAll();
        returnPlaceRepository.deleteAll();
        customerRepository.deleteAll();
        personalDataRepository.deleteAll();
        addressRepository.deleteAll();

        savedAddress = addressRepository.save(TestDataUtil.createTestAddressEntity());
    }


    @Test
    void shouldCreatePersonalData() throws Exception {
        PersonalDataRequest request = TestDataUtil.createTestPersonalDataRequest(savedAddress.getId());

        mockMvc.perform(post("/personalData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.first_name").value(request.getFirst_name()))
                .andExpect(jsonPath("$.last_name").value(request.getLast_name()));
    }

    @Test
    void shouldReturnListOfPersonalData() throws Exception {
        PersonalDataRequest request = TestDataUtil.createTestPersonalDataRequest(savedAddress.getId());

        mockMvc.perform(post("/personalData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/personalData")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].first_name").value(request.getFirst_name()));
    }

    @Test
    void shouldReturn404WhenAddressDoesNotExist() throws Exception {
        PersonalDataRequest request = TestDataUtil.createTestPersonalDataRequest(UUID.randomUUID());

        mockMvc.perform(post("/personalData")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
