package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceRequest;
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
class PickUpPlaceControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private PickUpPlaceRepository pickUpPlaceRepository;
    @Autowired private ReturnPlaceRepository returnPlaceRepository;
    @Autowired private RentalRepository rentalRepository;
    @Autowired private PaymentRepository paymentRepository;
    @Autowired private PersonalDataRepository personalDataRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private AddressRepository addressRepository;

    @Autowired private ObjectMapper objectMapper;

    private AddressEntity savedAddress;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();
        rentalRepository.deleteAll();
        returnPlaceRepository.deleteAll();
        pickUpPlaceRepository.deleteAll();
        customerRepository.deleteAll();
        personalDataRepository.deleteAll();
        addressRepository.deleteAll();

        savedAddress = addressRepository.save(TestDataUtil.createTestAddressEntity());
    }

    @Test
    void shouldReturnListOfPickUpPlaces() throws Exception {
        PickUpPlaceRequest request = TestDataUtil.createTestPickUpPlaceRequest(savedAddress.getId());

        mockMvc.perform(post("/pickUpPlaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/pickUpPlaces")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(request.getName()));
    }

    @Test
    void shouldReturn404WhenAddressDoesNotExist() throws Exception {
        PickUpPlaceRequest request = TestDataUtil.createTestPickUpPlaceRequest(UUID.randomUUID());

        mockMvc.perform(post("/pickUpPlaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
