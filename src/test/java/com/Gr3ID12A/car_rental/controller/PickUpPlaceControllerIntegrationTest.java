package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.pickUpPlace.PickUpPlaceRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.domain.entities.PickUpPlaceEntity;
import com.Gr3ID12A.car_rental.repositories.AddressRepository;
import com.Gr3ID12A.car_rental.repositories.PickUpPlaceRepository;
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
class PickUpPlaceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PickUpPlaceRepository pickUpPlaceRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AddressEntity savedAddress;

    @BeforeEach
    void setUp() {
        pickUpPlaceRepository.deleteAll();
        addressRepository.deleteAll();

        AddressEntity address = TestDataUtil.createTestAddressEntity();
        address.setId(null);
        savedAddress = addressRepository.save(address);
    }

    @Test
    void shouldReturnListOfPickUpPlaces() throws Exception {
        PickUpPlaceEntity place = PickUpPlaceEntity.builder()
                .name("Dworzec Centralny")
                .address(savedAddress)
                .build();
        pickUpPlaceRepository.save(place);

        mockMvc.perform(get("/pickUpPlaces")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Dworzec Centralny"));
    }

    @Test
    void shouldCreateNewPickUpPlace() throws Exception {
        PickUpPlaceRequest request = TestDataUtil.createTestPickUpPlaceRequest(savedAddress.getId());

        mockMvc.perform(post("/pickUpPlaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Lotnisko Chopina"));
    }

    @Test
    void shouldReturn404WhenAddressNotFound() throws Exception {
        PickUpPlaceRequest request = TestDataUtil.createTestPickUpPlaceRequest(UUID.randomUUID());

        mockMvc.perform(post("/pickUpPlaces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
