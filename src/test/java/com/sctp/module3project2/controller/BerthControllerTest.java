package com.sctp.module3project2.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sctp.module3project2.entity.Berth;
import com.sctp.module3project2.services.BerthService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BerthController.class)
class BerthControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private BerthService berthService;

    @Autowired
    private BerthController berthController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(berthController).build();
    }

    @Test
    void getAllBerthPortLocations() throws Exception {
        Berth berth1 = new Berth(1L, "Berth 1", "Location 1", true);
        Berth berth2 = new Berth(2L, "Berth 2", "Location 2", true);
        List<Berth> berths = Arrays.asList(berth1, berth2);

        when(berthService.getAllBerths()).thenReturn(berths);

        mockMvc.perform(get("/api/berth-location"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Berth 1"))
                .andExpect(jsonPath("$[0].location").value("Location 1"))
                .andExpect(jsonPath("$[0].availability").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Berth 2"))
                .andExpect(jsonPath("$[1].location").value("Location 2"))
                .andExpect(jsonPath("$[1].availability").value(true));

        verify(berthService, times(1)).getAllBerths();
        verifyNoMoreInteractions(berthService);
    }

    @Test
    void createBerthPortLocation() throws Exception {
        Berth berth = new Berth(null, "New Berth", "New Location", true);
        Berth savedBerth = new Berth(1L, "New Berth", "New Location", true);

        when(berthService.saveBerth(any(Berth.class))).thenReturn(savedBerth);

        mockMvc.perform(post("/api/berth-location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(berth)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Berth"))
                .andExpect(jsonPath("$.location").value("New Location"))
                .andExpect(jsonPath("$.availability").value(true));

        verify(berthService, times(1)).saveBerth(any(Berth.class));
        verifyNoMoreInteractions(berthService);
    }

    @Test
    void getBerthPortLocationById() throws Exception {
        Berth berth = new Berth(1L, "Berth 1", "Location 1", true);

        when(berthService.getBerthById(1L)).thenReturn(berth);

        mockMvc.perform(get("/api/berth-location/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Berth 1"))
                .andExpect(jsonPath("$.location").value("Location 1"))
                .andExpect(jsonPath("$.availability").value(true));

        verify(berthService, times(1)).getBerthById(1L);
        verifyNoMoreInteractions(berthService);
    }

    @Test
    void updateBerthPortLocation() throws Exception {
        Berth berth = new Berth(1L, "Berth 1", "Location 1", true);
        Berth updatedBerth = new Berth(1L, "Updated Berth", "Updated Location", false);

        when(berthService.getBerthById(1L)).thenReturn(berth);
        when(berthService.updateBerth(1L, berth)).thenReturn(updatedBerth);

        mockMvc.perform(put("/api/berth-location/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedBerth)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Berth"))
                .andExpect(jsonPath("$.location").value("Updated Location"))
                .andExpect(jsonPath("$.availability").value(false));

        verify(berthService, times(1)).getBerthById(1L);
        verify(berthService, times(1)).updateBerth(1L, berth);
        verifyNoMoreInteractions(berthService);
    }

    // @Test
    // void deleteBerthPortLocation() throws Exception {
    //     mockMvc.perform(delete("/api/berth-location/{id}", 1L))
    //             .andExpect(status().isNoContent());

    //     verify(berthService, times(1)).deleteBerth(1L);
    //     verifyNoMoreInteractions(berthService);
    // }

}
