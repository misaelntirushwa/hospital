package com.ntm.hospital.api;

import com.ntm.hospital.data.Physician;
import com.ntm.hospital.exception.PhysicianNotFoundException;
import com.ntm.hospital.service.PhysicianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PhysicianControllerTest {

    @Mock
    PhysicianService physicianService;

    @InjectMocks
    PhysicianController physicianController;

    MockMvc mockMvc;

    Physician validPhysician;

    @BeforeEach
    void setUp() {
        validPhysician = new Physician(1, "Jean Tremblay", "Anesthesiology", 23987);

        mockMvc = MockMvcBuilders.standaloneSetup(physicianController).build();
    }

    @Test
    void shouldGetStatusOkWhenPhysicianIdExists() {
        given(physicianService.findPhysician(any())).willReturn(validPhysician);

        try {
            mockMvc.perform(get("/api/v1/physicians/" + validPhysician.getId()))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldGetStatusNotFoundWhenPhysicianIdDoesNotExist() {
        given(physicianService.findPhysician(any()))
                .willThrow(new PhysicianNotFoundException("Physician Not Found"));

        try {
            mockMvc.perform(get("/api/v1/physicians/" + 4))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}