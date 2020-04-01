package com.ntm.hospital.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntm.hospital.data.Physician;
import com.ntm.hospital.exception.PhysicianNotFoundException;
import com.ntm.hospital.service.PhysicianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PhysicianControllerTest {

    @Mock
    PhysicianService physicianService;

    @InjectMocks
    PhysicianController physicianController;

    MockMvc mockMvc;

    Physician validPhysician;

    List<Physician> physicianList;

    @BeforeEach
    void setUp() {
        validPhysician = new Physician(1, "Jean Tremblay", "Anesthesiology", 23987);
        physicianList = new ArrayList<>();
        physicianList.add(validPhysician);
        physicianList.add(new Physician(2, "Martin Gagnon", "Dermatopathology", 53123));
        physicianList.add(new Physician(3, "Louise Duclos", "Family Medicine", 91823));
        mockMvc = MockMvcBuilders.standaloneSetup(physicianController).build();
    }

    @Test
    void shouldGetPhysicianList() throws Exception {
        given(physicianService.listPhysicians()).willReturn(physicianList);

        mockMvc.perform(get("/api/v1/physicians")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Jean Tremblay")))
                .andExpect(jsonPath("$[0].position", is("Anesthesiology")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Martin Gagnon")))
                .andExpect(jsonPath("$[1].position", is("Dermatopathology")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Louise Duclos")))
                .andExpect(jsonPath("$[2].position", is("Family Medicine")));
    }

    @Test
    void shouldGetEmptyPhysicianList() throws Exception {
        given(physicianService.listPhysicians()).willReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/physicians")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldGetPhysicianById() throws Exception {
        given(physicianService.findPhysician(any())).willReturn(validPhysician);

        mockMvc.perform(get("/api/v1/physicians/" + validPhysician.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", is(validPhysician.getId())))
                .andExpect(jsonPath("$.name", is(validPhysician.getName())))
                .andExpect(jsonPath("$.position", is(validPhysician.getPosition())))
                .andExpect(jsonPath("$.ssn", is(validPhysician.getSsn())));
    }

    @Test
    void shouldGetNotFoundWhenPhysicianDoesNotExist() throws Exception {
        given(physicianService.findPhysician(any()))
                .willThrow(new PhysicianNotFoundException("Physician Not Found"));

        mockMvc.perform(get("/api/v1/physicians/" + 4))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldCreateNewPhysician() throws Exception {
        Physician newPhysician = new Physician();
        newPhysician.setName("Jacques Poulin");
        newPhysician.setPosition("Diagnostic Radiology");
        newPhysician.setSsn(123456);

        Physician savedPhysician = new Physician(1, newPhysician.getName(),
                newPhysician.getPosition(), newPhysician.getSsn());

        given(physicianService.addPhysician(newPhysician)).willReturn(savedPhysician);

        mockMvc.perform(post("/api/v1/physicians")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(newPhysician)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

    }

    @Test
    void shouldChangeExistingPhysician() throws Exception {

        given(physicianService.changePhysician(2, new Physician(null, "Marco Polo", "Ophtamology", 9923)))
                .willReturn(new Physician(2, "Marco Polo", "Ophtamology", 9923));

        mockMvc.perform(put("/api/v1/physicians/" + 2)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(new Physician(null, "Marco Polo", "Ophtamology", 9923))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Marco Polo")))
                .andExpect(jsonPath("$.position", is("Ophtamology")))
                .andExpect(jsonPath("$.ssn", is(9923)));
    }

    private static String asJsonString(final Physician physician) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(physician);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}