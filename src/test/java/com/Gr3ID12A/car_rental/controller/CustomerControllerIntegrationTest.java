package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.customer.CustomerRequest;
import com.Gr3ID12A.car_rental.domain.dto.user.AuthResponse;
import com.Gr3ID12A.car_rental.domain.dto.user.UserRequest;
import com.Gr3ID12A.car_rental.domain.entities.*;
import com.Gr3ID12A.car_rental.domain.entities.role.RoleEntity;
import com.Gr3ID12A.car_rental.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private CustomerRepository customerRepository;
    @Autowired private PersonalDataRepository personalDataRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private TokenRepository tokenRepository;

    private String authToken;
    private PersonalDataEntity savedPersonalData;
    private UserEntity savedUser;
    private CustomerEntity savedCustomer;

    @BeforeEach
    void setup() throws Exception {
        customerRepository.deleteAll();
        personalDataRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        addressRepository.deleteAll();
        tokenRepository.deleteAll();

        RoleEntity role = roleRepository.save(TestDataUtil.createTestAdminRole());
        savedUser = userRepository.save(TestDataUtil.createTestLocalUserEntityWithRole(role));
        AddressEntity address = addressRepository.save(TestDataUtil.createTestAddressEntity());
        savedPersonalData = personalDataRepository.save(TestDataUtil.createTestPersonalDataEntityWithAddressGivven(address));
        savedCustomer = customerRepository.save(TestDataUtil.createTestCustomerEntity(savedUser, savedPersonalData));

        UserRequest loginRequest = UserRequest.builder()
                .email(savedUser.getEmail())
                .password("testPassword")
                .build();

        String loginResponse = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AuthResponse authResponse = objectMapper.readValue(loginResponse, AuthResponse.class);
        authToken = authResponse.getToken();
    }

    @Test
    void shouldListAllCustomers() throws Exception {
        mockMvc.perform(get("/customers")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].personalData.first_name").value("Jan"));
    }

    @Test
    void shouldReturnCustomerById() throws Exception {
        mockMvc.perform(get("/customers/" + savedCustomer.getId())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loyalty_points").value(0));
    }

    @Test
    void shouldReturn404WhenCustomerNotFound() throws Exception {
        mockMvc.perform(get("/customers/" + UUID.randomUUID())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewCustomer() throws Exception {
        CustomerRequest request = TestDataUtil.createTestCustomerRequest(savedPersonalData.getId(), savedUser.getId());

        mockMvc.perform(post("/customers")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.loyalty_points").value(200));
    }

    @Test
    void shouldReturn404WhenCreatingCustomerWithInvalidPersonalData() throws Exception {
        CustomerRequest request = TestDataUtil.createTestCustomerRequest(UUID.randomUUID(), savedUser.getId());

        mockMvc.perform(post("/customers")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldPartiallyUpdateCustomer() throws Exception {
        CustomerRequest updateRequest = CustomerRequest.builder()
                .personalDataId(savedPersonalData.getId())
                .userId(savedUser.getId())
                .loyalty_points(999)
                .build();

        mockMvc.perform(patch("/customers/" + savedCustomer.getId())
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loyalty_points").value(999));
    }

    @Test
    void shouldReturn404WhenUpdatingNonExistingCustomer() throws Exception {
        CustomerRequest request = TestDataUtil.createTestCustomerRequest(savedPersonalData.getId(), savedUser.getId());

        mockMvc.perform(patch("/customers/" + UUID.randomUUID())
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/customers/" + savedCustomer.getId())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }
}
