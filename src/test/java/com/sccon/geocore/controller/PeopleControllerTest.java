package com.sccon.geocore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sccon.geocore.repository.InMemoryPersonRepository;
import com.sccon.geocore.repository.PersonRepository;
import com.sccon.geocore.service.PersonService;
import com.sccon.geocore.web.dto.CreatePersonRequest;
import com.sccon.geocore.web.dto.UpdatePersonRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para o PeopleController.
 * 
 * @author SCCON Challenge
 * @version 1.0
 */
@WebMvcTest(PeopleController.class)
class PeopleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    private Clock fixedClock;

    @BeforeEach
    void setUp() {
        fixedClock = Clock.fixed(
            LocalDate.of(2023, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault()
        );
    }

    @Test
    void testGetAllPeople_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/people"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetPersonById_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/people/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePerson_ShouldReturnCreated() throws Exception {
        CreatePersonRequest request = new CreatePersonRequest(
                "João Silva",
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2020, 1, 1)
        );

        mockMvc.perform(post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePersonWithId_ShouldReturnCreated() throws Exception {
        CreatePersonRequest request = new CreatePersonRequest(
                "João Silva",
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2020, 1, 1)
        );

        mockMvc.perform(post("/people/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdatePerson_ShouldReturnOk() throws Exception {
        CreatePersonRequest request = new CreatePersonRequest(
                "João Silva Atualizado",
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2020, 1, 1)
        );

        mockMvc.perform(put("/people/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testPatchPerson_ShouldReturnOk() throws Exception {
        UpdatePersonRequest request = new UpdatePersonRequest(
                Optional.of("João Silva Atualizado"),
                Optional.empty(),
                Optional.empty()
        );

        mockMvc.perform(patch("/people/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePerson_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/people/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetPersonAge_ShouldReturnOk() throws Exception {
        when(personService.calculateAge(anyLong(), any())).thenReturn(22L);

        mockMvc.perform(get("/people/1/age")
                        .param("output", "years"))
                .andExpect(status().isOk())
                .andExpect(content().string("22"));
    }

    @Test
    void testGetPersonSalary_ShouldReturnOk() throws Exception {
        when(personService.calculateSalary(anyLong(), any())).thenReturn(3250.36);

        mockMvc.perform(get("/people/1/salary")
                        .param("output", "full"))
                .andExpect(status().isOk())
                .andExpect(content().string("3250.36"));
    }

    @Test
    void testCreatePerson_InvalidData_ShouldReturnBadRequest() throws Exception {
        CreatePersonRequest request = new CreatePersonRequest(
                "", // Nome vazio
                LocalDate.of(1990, 1, 1),
                LocalDate.of(2020, 1, 1)
        );

        mockMvc.perform(post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetPersonAge_InvalidOutput_ShouldReturnBadRequest() throws Exception {
        when(personService.calculateAge(anyLong(), any()))
                .thenThrow(new IllegalArgumentException("Tipo de saída inválido"));

        mockMvc.perform(get("/people/1/age")
                        .param("output", "invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetPersonSalary_InvalidOutput_ShouldReturnBadRequest() throws Exception {
        when(personService.calculateSalary(anyLong(), any()))
                .thenThrow(new IllegalArgumentException("Tipo de saída inválido"));

        mockMvc.perform(get("/people/1/salary")
                        .param("output", "invalid"))
                .andExpect(status().isBadRequest());
    }
}
