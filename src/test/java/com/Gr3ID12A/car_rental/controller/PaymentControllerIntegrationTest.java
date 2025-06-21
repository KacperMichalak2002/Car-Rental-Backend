package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.payment.PaymentRequest;
import com.Gr3ID12A.car_rental.domain.entities.*;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentName;
import com.Gr3ID12A.car_rental.domain.entities.paymentType.PaymentTypeEntity;
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

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@WithMockUser
public class PaymentControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private AddressRepository addressRepository;
    @Autowired private PersonalDataRepository personalDataRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private CarRepository carRepository;
    @Autowired private BodyTypeRepository bodyTypeRepository;
    @Autowired private MakeRepository makeRepository;
    @Autowired private ModelRepository modelRepository;
    @Autowired private SpecificationRepository specificationRepository;
    @Autowired private ReturnPlaceRepository returnPlaceRepository;
    @Autowired private PickUpPlaceRepository pickUpPlaceRepository;
    @Autowired private RentalRepository rentalRepository;
    @Autowired private PaymentTypeRepository paymentTypeRepository;
    @Autowired private PaymentRepository paymentRepository;

    private RentalEntity savedRental;
    private PaymentTypeEntity savedPaymentType;
    private PaymentEntity savedPayment;

    @BeforeEach
    void setUp() {
        paymentRepository.deleteAll();
        rentalRepository.deleteAll();
        returnPlaceRepository.deleteAll();
        pickUpPlaceRepository.deleteAll();
        carRepository.deleteAll();
        specificationRepository.deleteAll();
        modelRepository.deleteAll();
        bodyTypeRepository.deleteAll();
        makeRepository.deleteAll();
        customerRepository.deleteAll();
        personalDataRepository.deleteAll();
        addressRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        paymentTypeRepository.deleteAll();

        var role = roleRepository.save(TestDataUtil.createTestAdminRole());
        var user = userRepository.save(TestDataUtil.createTestLocalUserEntityWithRole(role));
        var address = addressRepository.save(TestDataUtil.createTestAddressEntity());
        var personalData = personalDataRepository.save(TestDataUtil.createTestPersonalDataEntityWithAddressGivven(address));
        var customer = customerRepository.save(TestDataUtil.createTestCustomer(user, personalData));
        var make = makeRepository.save(TestDataUtil.createTestMakeEntity());
        var bodyType = bodyTypeRepository.save(TestDataUtil.createTestBodyTypeEntity());
        var model = modelRepository.save(TestDataUtil.createTestModelEntity(make, bodyType));
        var spec = specificationRepository.save(TestDataUtil.createTestSpecificationEntity());
        var car = carRepository.save(TestDataUtil.createTestCarEntity(model, spec));
        var pickUp = pickUpPlaceRepository.save(TestDataUtil.createTestPickUpPlaceEntity(address));
        var returnPlace = returnPlaceRepository.save(TestDataUtil.createTestReturnPlaceEntity(address));
        savedRental = rentalRepository.save(TestDataUtil.createTestRentalEntity(customer, car, pickUp, returnPlace));
        savedPaymentType = paymentTypeRepository.save(TestDataUtil.createTestPaymentTypeEntity(PaymentName.ONLINE));
        savedPayment = paymentRepository.save(TestDataUtil.createTestPaymentEntity(savedRental, savedPaymentType));
    }

    @Test
    void shouldReturnListOfPayments() throws Exception {
        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(savedPayment.getTitle()))
                .andExpect(jsonPath("$[0].status").value(savedPayment.getStatus()));
    }

    @Test
    void shouldReturnPaymentById() throws Exception {
        mockMvc.perform(get("/payments/" + savedPayment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(savedPayment.getTitle()));
    }

    @Test
    void shouldReturn404WhenPaymentNotFound() throws Exception {
        mockMvc.perform(get("/payments/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreatePayment() throws Exception {
        PaymentRequest request = PaymentRequest.builder()
                .title("Nowa płatność")
                .rentalId(savedRental.getId())
                .cost(BigDecimal.valueOf(1000.0))
                .paymentType(PaymentName.OFFLINE)
                .build();

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    void shouldReturn404WhenRentalDoesNotExist() throws Exception {
        PaymentRequest request = PaymentRequest.builder()
                .title("Invalid Rental")
                .rentalId(UUID.randomUUID())
                .cost(BigDecimal.valueOf(1500.0))
                .paymentType(PaymentName.ONLINE)
                .build();

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Rental not found"));
    }

    @Test
    void shouldConfirmOfflinePayment() throws Exception {
        mockMvc.perform(post("/payments/" + savedPayment.getId() + "/confirm-offline"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void shouldReturn404WhenOfflineConfirmFails() throws Exception {
        mockMvc.perform(post("/payments/" + UUID.randomUUID() + "/confirm-offline"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldPartiallyUpdatePaymentStatus() throws Exception {
        PaymentRequest request = PaymentRequest.builder()
                .title(null)
                .status("PAID")
                .paymentType(null)
                .cost(null)
                .rentalId(null)
                .build();

        mockMvc.perform(patch("/payments/" + savedPayment.getId() + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenPatchStatusFails() throws Exception {
        mockMvc.perform(patch("/payments/" + UUID.randomUUID() + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }
}
