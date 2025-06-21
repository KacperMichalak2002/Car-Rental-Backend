package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.model.ModelRequest;
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
public class ModelControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private PersonalDataRepository personalDataRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private TokenRepository tokenRepository;
    @Autowired private MakeRepository makeRepository;
    @Autowired private BodyTypeRepository bodyTypeRepository;
    @Autowired private ModelRepository modelRepository;

    private String authToken;

    @BeforeEach
    void setup() throws Exception {
        modelRepository.deleteAll();
        makeRepository.deleteAll();
        bodyTypeRepository.deleteAll();
        tokenRepository.deleteAll();
        customerRepository.deleteAll();
        personalDataRepository.deleteAll();
        addressRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        RoleEntity role = roleRepository.save(TestDataUtil.createTestAdminRole());
        UserEntity user = userRepository.save(TestDataUtil.createTestLocalUserEntityWithRole(role));
        AddressEntity address = addressRepository.save(TestDataUtil.createTestAddressEntity());
        PersonalDataEntity personalData = personalDataRepository.save(
                TestDataUtil.createTestPersonalDataEntityWithAddressGivven(address)
        );
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
        authToken = authResponse.getToken();
    }

    @Test
    void shouldListAllModels() throws Exception {
        MakeEntity make = makeRepository.save(MakeEntity.builder().name("Toyota").build());
        BodyTypeEntity bodyType = bodyTypeRepository.save(BodyTypeEntity.builder().name("SUV").build());

        modelRepository.save(ModelEntity.builder()
                .name("RAV4")
                .make(make)
                .bodyType(bodyType)
                .build());

        mockMvc.perform(get("/models")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.name == 'RAV4')]").exists());
    }

    @Test
    void shouldCreateModel() throws Exception {
        MakeEntity make = makeRepository.save(MakeEntity.builder().name("Toyota").build());
        BodyTypeEntity bodyType = bodyTypeRepository.save(BodyTypeEntity.builder().name("SUV").build());

        ModelRequest request = ModelRequest.builder()
                .makeId(make.getId())
                .bodyTypeId(bodyType.getId())
                .name("Camry")
                .build();

        mockMvc.perform(post("/models")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Camry"))
                .andExpect(jsonPath("$.make.id").value(make.getId().toString()))
                .andExpect(jsonPath("$.bodyType.id").value(bodyType.getId().toString()));
    }


    @Test
    void shouldReturn404WhenCreatingModelWithNonExistingMakeOrBodyType() throws Exception {
        ModelRequest request = ModelRequest.builder()
                .name("Camry")
                .makeId(UUID.randomUUID())
                .bodyTypeId(UUID.randomUUID())
                .build();

        mockMvc.perform(post("/models")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteModel() throws Exception {
        MakeEntity make = makeRepository.save(MakeEntity.builder().name("Toyota").build());
        BodyTypeEntity bodyType = bodyTypeRepository.save(BodyTypeEntity.builder().name("Sedan").build());

        ModelEntity model = modelRepository.save(ModelEntity.builder()
                .name("Corolla")
                .make(make)
                .bodyType(bodyType)
                .build());

        mockMvc.perform(delete("/models/" + model.getId())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }
}
