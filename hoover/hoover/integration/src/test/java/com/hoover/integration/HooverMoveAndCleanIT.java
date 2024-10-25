package com.hoover.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoover.IntegrationBaseApplication;
import com.hoover.adapters.http.inbound.commands.StartHooverCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = IntegrationBaseApplication.class)
@AutoConfigureMockMvc
public class HooverMoveAndCleanIT {
    @Autowired
    private ObjectMapper objectMapper;
    StartHooverCommand command = new StartHooverCommand(
            new int[]{5, 5},
            new int[]{2, 2},
            List.of(new Integer[]{1, 2}, new Integer[]{3, 4}),
            "NNESEESWNWW"
    );
    private final String finalResult = "{\"coords\":[1,3],\"patches\":1}";

    @Autowired
    private MockMvc mockMvc;
    @Test
    @WithMockUser
    public void testMoveAndCleanEndpointShouldReturn200() throws Exception {
        String jsonCommand = objectMapper.writeValueAsString(command);
        mockMvc.perform(post("/robotapi/v1/hoover/clean-room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCommand)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coords.x").value(2))
                .andExpect(jsonPath("$.coords.y").value(3))
                .andExpect(jsonPath("$.patches").value(1));
    }

    @Test
    @WithMockUser
    public void testMoveAndCleanEndpointShouldReturn400BadRequest() throws Exception {
        String jsonCommand = objectMapper.writeValueAsString(command);
        mockMvc.perform(post("/robotapi/v1/hoover/clean-room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCommand)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coords.x").value(2))
                .andExpect(jsonPath("$.coords.y").value(3))
                .andExpect(jsonPath("$.patches").value(1));
    }

    @Test
    @WithMockUser
    public void testMoveAndCleanEndpointShouldReturn400() throws Exception {
        StartHooverCommand command = new StartHooverCommand(
                new int[]{5, 5},
                new int[]{10, 10},
                List.of(new Integer[]{1, 2}, new Integer[]{3, 4}),
                "NNESEESWNWW"
        );
        String jsonCommand = objectMapper.writeValueAsString(command);
        mockMvc.perform(post("/robotapi/v1/hoover/clean-room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                        .with(csrf()))
                .andExpect(status().isBadRequest());

    }
}
