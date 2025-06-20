package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MakeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MakeRepository makeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private String authToken;

    @BeforeEach
    void setup() throws Exception {
        makeRepository.deleteAll();
        tokenRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        customerRepository.deleteAll();
        personalDataRepository.deleteAll();
        addressRepository.deleteAll();

        RoleEntity role = roleRepository.save(TestDataUtil.createTestAdminRole());
        UserEntity user = userRepository.save(TestDataUtil.createTestLocalUserEntityWithRole(role));
        AddressEntity address = addressRepository.save(TestDataUtil.createTestAddressEntity());
        PersonalDataEntity personalDataEntity = personalDataRepository.save(TestDataUtil.createTestPersonalDataEntityWithAddressGivven(address));
        customerRepository.save(TestDataUtil.createTestCustomer(user, personalDataEntity));

        UserRequest loginRequest = UserRequest.builder()
                .email(user.getEmail())
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
    void shouldListAllMakes() throws Exception {
        makeRepository.save(MakeEntity.builder().name("Toyota").build());

        mockMvc.perform(get("/makes")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.name == 'Toyota')]").exists());
    }

}
