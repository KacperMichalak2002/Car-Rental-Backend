package com.Gr3ID12A.car_rental.controller;

import com.Gr3ID12A.car_rental.domain.entities.BodyTypeEntity;
import com.Gr3ID12A.car_rental.repositories.BodyTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BodyTypeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BodyTypeRepository bodyTypeRepository;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldListAllBodyTypes() throws Exception {
        mockMvc.perform(get("/bodyTypes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.name == 'SUV')]").exists());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateBodyType() throws Exception {
        String bodyTypeJson = "{\"name\":\"SUV\"}";

        mockMvc.perform(post("/bodyTypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyTypeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("SUV"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldDeleteBodyType() throws Exception {
        BodyTypeEntity saved = bodyTypeRepository.save(
                BodyTypeEntity.builder().name("Coupe").build());

        UUID id = saved.getId();

        mockMvc.perform(delete("/bodyTypes/" + id))
                .andExpect(status().isNoContent());

        boolean exists = bodyTypeRepository.existsById(id);
        assertThat(exists).isFalse();
    }
}
