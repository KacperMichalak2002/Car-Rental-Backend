package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.car.CarRequest;
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

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CarControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private MakeRepository makeRepository;
    @Autowired private BodyTypeRepository bodyTypeRepository;
    @Autowired private ModelRepository modelRepository;
    @Autowired private SpecificationRepository specificationRepository;
    @Autowired private CarRepository carRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private PersonalDataRepository personalDataRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private TokenRepository tokenRepository;
    @Autowired private ObjectMapper objectMapper;

    private String authToken;
    private ModelEntity savedModel;
    private SpecificationEntity savedSpec;
    private CarEntity savedCar;

    @BeforeEach
    void setup() throws Exception {
        tokenRepository.deleteAll();
        carRepository.deleteAll();
        specificationRepository.deleteAll();
        modelRepository.deleteAll();
        makeRepository.deleteAll();
        bodyTypeRepository.deleteAll();
        customerRepository.deleteAll();
        personalDataRepository.deleteAll();
        addressRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        RoleEntity role = roleRepository.save(TestDataUtil.createTestAdminRole());
        UserEntity user = userRepository.save(TestDataUtil.createTestLocalUserEntityWithRole(role));
        AddressEntity address = addressRepository.save(TestDataUtil.createTestAddressEntity());
        PersonalDataEntity personalData = personalDataRepository.save(
                TestDataUtil.createTestPersonalDataEntityWithAddressGivven(address));
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

        MakeEntity make = makeRepository.save(TestDataUtil.createTestMakeEntity());
        BodyTypeEntity bodyType = bodyTypeRepository.save(TestDataUtil.createTestBodyTypeEntity());

        savedModel = modelRepository.save(ModelEntity.builder()
                .name("Touran")
                .make(make)
                .bodyType(bodyType)
                .build());

        savedSpec = specificationRepository.save(TestDataUtil.createTestSpecificationEntity());

        savedCar = carRepository.save(TestDataUtil.createTestCarEntity(savedModel, savedSpec));
    }

    @Test
    void shouldListAllCars() throws Exception {
        mockMvc.perform(get("/cars")
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].availability").value("AVAILABLE"))
                .andExpect(jsonPath("$[0].description").value("Nowoczesny samochód z automatyczną skrzynią biegów"));
    }

    @Test
    void shouldGetCarById() throws Exception {
        mockMvc.perform(get("/cars/" + savedCar.getId())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(savedCar.getDescription()));
    }

    @Test
    void shouldReturnNotFoundForMissingCar() throws Exception {
        mockMvc.perform(get("/cars/" + UUID.randomUUID())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewCar() throws Exception {
        SpecificationEntity modifiedSpec = TestDataUtil.createTestSpecificationEntity();
        modifiedSpec.setEngineCapacity(2.2);
        SpecificationEntity newSpec = specificationRepository.save(modifiedSpec);

        CarRequest request = CarRequest.builder()
                .availability("Available")
                .cost(BigDecimal.valueOf(199.99))
                .deposit(BigDecimal.valueOf(99.99))
                .description("Nowy samochód testowy")
                .image_url("/images/car.png")
                .modelId(savedModel.getId())
                .specificationId(newSpec.getId())
                .build();

        mockMvc.perform(post("/cars")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("Nowy samochód testowy"));
    }


    @Test
    void shouldReturnNotFoundWhenCreatingCarWithInvalidDependencies() throws Exception {
        CarRequest request = CarRequest.builder()
                .availability("Available")
                .cost(BigDecimal.valueOf(100.00))
                .deposit(BigDecimal.valueOf(50.00))
                .description("Błąd zależności")
                .image_url("/invalid/img.png")
                .modelId(UUID.randomUUID())
                .specificationId(UUID.randomUUID())
                .build();

        mockMvc.perform(post("/cars")
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldPatchCar() throws Exception {
        CarRequest patchRequest = CarRequest.builder()
                .description("Zmieniony opis")
                .build();

        mockMvc.perform(patch("/cars/" + savedCar.getId())
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Zmieniony opis"));
    }

    @Test
    void shouldReturnNotFoundWhenPatchingMissingCar() throws Exception {
        mockMvc.perform(patch("/cars/" + UUID.randomUUID())
                        .header("Authorization", "Bearer " + authToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCar() throws Exception {
        mockMvc.perform(delete("/cars/" + savedCar.getId())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }
}
