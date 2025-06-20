package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.address.AddressRequest;
import com.Gr3ID12A.car_rental.domain.entities.AddressEntity;
import com.Gr3ID12A.car_rental.repositories.AddressRepository;
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
class AddressControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AddressEntity savedAddress;

    @BeforeEach
    void setUp() {
        addressRepository.deleteAll();
        AddressEntity address = TestDataUtil.createTestAddressEntity();
        address.setId(null);
        savedAddress = addressRepository.save(address);
    }

    @Test
    void shouldReturnListOfAddresses() throws Exception {
        mockMvc.perform(get("/addresses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].city").value("Kielce"));
    }

    @Test
    void shouldReturnAddressById() throws Exception {
        mockMvc.perform(get("/addresses/" + savedAddress.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Kielce"));
    }

    @Test
    void shouldReturn404WhenAddressNotFound() throws Exception {
        mockMvc.perform(get("/addresses/" + UUID.randomUUID())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewAddress() throws Exception {
        AddressRequest request = TestDataUtil.createTestAddressRequest();

        mockMvc.perform(post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value("Kielce"));
    }

    @Test
    void shouldUpdateAddressPartially() throws Exception {
        AddressRequest updateRequest = TestDataUtil.createUpdatedAddressRequest();


        mockMvc.perform(patch("/addresses/" + savedAddress.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Warszawa"));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingAddress() throws Exception {
        AddressRequest updateRequest = TestDataUtil.createTestAddressRequest();

        mockMvc.perform(patch("/addresses/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());
    }
}
