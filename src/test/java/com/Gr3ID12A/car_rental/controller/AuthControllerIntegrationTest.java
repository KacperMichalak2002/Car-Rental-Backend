package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.refreshToken.RefreshTokenRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PersonalDataRepository personalDataRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    void setup() {
        tokenRepository.deleteAll();
        customerRepository.deleteAll();
        personalDataRepository.deleteAll();
        addressRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        roleRepository.save(TestDataUtil.createTestAdminRole());
        UserRequest userRequest = TestDataUtil.createTestUserRequest();

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered"));
    }

    @Test
    void shouldLoginSuccessfullyAndReturnTokens() throws Exception {
        RoleEntity role = roleRepository.save(TestDataUtil.createTestAdminRole());
        UserEntity user = userRepository.save(TestDataUtil.createTestLocalUserEntityWithRole(role));
        AddressEntity address = addressRepository.save(TestDataUtil.createTestAddressEntity());
        PersonalDataEntity personalData = personalDataRepository.save(TestDataUtil.createTestPersonalDataEntityWithAddressGivven(address));
        customerRepository.save(TestDataUtil.createTestCustomer(user, personalData));

        UserRequest loginRequest = UserRequest.builder()
                .email(user.getEmail())
                .password("testPassword")
                .build();

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    void shouldReturnNewAccessToken_WhenValidRefreshToken() throws Exception {
        RoleEntity role = roleRepository.save(TestDataUtil.createTestAdminRole());
        UserEntity user = userRepository.save(TestDataUtil.createTestLocalUserEntityWithRole(role));
        AddressEntity address = addressRepository.save(TestDataUtil.createTestAddressEntity());
        PersonalDataEntity personalData = personalDataRepository.save(TestDataUtil.createTestPersonalDataEntityWithAddressGivven(address));
        customerRepository.save(TestDataUtil.createTestCustomer(user, personalData));

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

        RefreshTokenRequest refreshRequest = new RefreshTokenRequest(authResponse.getRefreshToken());

        mockMvc.perform(post("/auth/refreshToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refreshRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authToken").exists());
    }
}
