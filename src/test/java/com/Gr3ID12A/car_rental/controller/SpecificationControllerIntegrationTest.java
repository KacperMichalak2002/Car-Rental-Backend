package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.specification.SpecificationRequest;
import com.Gr3ID12A.car_rental.repositories.CarRepository;
import com.Gr3ID12A.car_rental.repositories.SpecificationRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@WithMockUser
public class SpecificationControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private SpecificationRepository specificationRepository;
    @Autowired private CarRepository carRepository;
    @Autowired private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        specificationRepository.deleteAll();
    }

    @Test
    void shouldCreateSpecification() throws Exception {
        SpecificationRequest request = TestDataUtil.createTestSpecificationRequest();

        mockMvc.perform(post("/specifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.registration").value(request.getRegistration()))
                .andExpect(jsonPath("$.vin").value(request.getVin()));
    }

    @Test
    void shouldReturnListOfSpecifications() throws Exception {
        SpecificationRequest request = TestDataUtil.createTestSpecificationRequest();

        mockMvc.perform(post("/specifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/specifications")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].registration").value(request.getRegistration()));
    }

    @Test
    void shouldReturnSpecificationById() throws Exception {
        SpecificationRequest request = TestDataUtil.createTestSpecificationRequest();

        String response = mockMvc.perform(post("/specifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(get("/specifications/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.registration").value(request.getRegistration()));
    }

    @Test
    void shouldReturn404ForNonExistingSpecification() throws Exception {
        mockMvc.perform(get("/specifications/00000000-0000-0000-0000-000000000000")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
