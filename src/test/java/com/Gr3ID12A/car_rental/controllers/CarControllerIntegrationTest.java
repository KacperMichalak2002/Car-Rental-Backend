package com.Gr3ID12A.car_rental.controllers;

import com.Gr3ID12A.car_rental.TestDataUtil;
import com.Gr3ID12A.car_rental.domain.dto.car.CarRequest;
import com.Gr3ID12A.car_rental.domain.entities.BodyTypeEntity;
import com.Gr3ID12A.car_rental.domain.entities.ModelEntity;
import com.Gr3ID12A.car_rental.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc

public class CarControllerIntegrationTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private CarService carService;
    private String endpointUrl = "/cars";

    @Autowired
    public CarControllerIntegrationTest(MockMvc mockMvc, CarService carService){
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.carService = carService;
    }

    @Test
    public void testThatCreateCarSuccessfullyReturnsHttpsStatus201Created() throws Exception{
        UUID modelId = UUID.randomUUID();
        UUID specificationID = UUID.randomUUID();

        CarRequest carRequest = TestDataUtil.createCarRequestTestA(modelId,specificationID);
        String createCarJson = objectMapper.writeValueAsString(carRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.post(endpointUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCarJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

//    @Test
//    public void testThatCreateCarSuccessfullyReturnsSavedCar() throws Exception{
//// Need to save the model to the db first add this functionality NEED TO BE FINISHED
//        ModelEntity model = TestDataUtil.createModelEntityA();
//        BodyTypeEntity bodyType = TestDataUtil.createBodyTypeEntityA();
//
//        model.setBodyType(bodyType);
//
//        UUID specificationID = UUID.randomUUID();
//
//        CarRequest carRequest = TestDataUtil.createCarRequestTestA(model.getId(),specificationID);
//        String createCarJson = objectMapper.writeValueAsString(carRequest);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.post(endpointUrl)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(createCarJson)
//        ).andExpect(
//                MockMvcResultMatchers.jsonPath("$.model").value(carRequest.getModelId())
//        );
//    }
}
