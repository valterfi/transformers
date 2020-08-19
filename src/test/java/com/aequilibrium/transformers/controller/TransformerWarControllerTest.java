package com.aequilibrium.transformers.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author valterfi
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TransformerWarControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldExecuteTransformerWar() throws Exception {
		List<Long> ids = new ArrayList<Long>(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L));
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/transformers/war")
			      .content(asJsonString(ids))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.battlesNumber").value(7))
			      .andExpect(jsonPath("$.battleStatus").value("FINISHED"))
			      .andExpect(jsonPath("$.winningTeam").value("DECEPTICON"))
			      .andExpect(jsonPath("$.message").value("The winning team is deceptions"))
			      .andExpect(jsonPath("$.losingTeamSurvivors").value("Transformer10, Transformer1, Transformer6"))
			      .andExpect(jsonPath("$.details").isNotEmpty());
	}
	
	@Test
	public void shouldReturnSummaryTransformerWar() throws Exception {
		List<Long> ids = new ArrayList<Long>(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L));

		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/transformers/war")
			      .content(asJsonString(ids))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.battlesNumber").value(7))
			      .andExpect(jsonPath("$.battleStatus").value("FINISHED"))
			      .andExpect(jsonPath("$.winningTeam").value("DECEPTICON"))
			      .andExpect(jsonPath("$.message").value("The winning team is deceptions"))
			      .andExpect(jsonPath("$.losingTeamSurvivors").value("Transformer10, Transformer1, Transformer6"))
			      .andExpect(jsonPath("$.details").isNotEmpty());
		
		mockMvc.perform( MockMvcRequestBuilders
			      .get("/api/transformers/war")
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.battlesNumber").value(7))
			      .andExpect(jsonPath("$.battleStatus").value("FINISHED"))
			      .andExpect(jsonPath("$.winningTeam").value("DECEPTICON"))
			      .andExpect(jsonPath("$.message").value("The winning team is deceptions"))
			      .andExpect(jsonPath("$.losingTeamSurvivors").value("Transformer10, Transformer1, Transformer6"))
			      .andExpect(jsonPath("$.details").isNotEmpty());
	}
	
	@Test
	public void shouldReturnNoVerboseSummaryTransformerWar() throws Exception {
		List<Long> ids = new ArrayList<Long>(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L));
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/transformers/war")
			      .content(asJsonString(ids))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.battlesNumber").value(7))
			      .andExpect(jsonPath("$.battleStatus").value("FINISHED"))
			      .andExpect(jsonPath("$.winningTeam").value("DECEPTICON"))
			      .andExpect(jsonPath("$.message").value("The winning team is deceptions"))
			      .andExpect(jsonPath("$.losingTeamSurvivors").value("Transformer10, Transformer1, Transformer6"))
			      .andExpect(jsonPath("$.details").isNotEmpty());
		
		mockMvc.perform( MockMvcRequestBuilders
			      .get("/api/transformers/war/noverbose")
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(jsonPath("$.battlesNumber").value(7))
			      .andExpect(jsonPath("$.battleStatus").value("FINISHED"))
			      .andExpect(jsonPath("$.winningTeam").value("DECEPTICON"))
			      .andExpect(jsonPath("$.message").value("The winning team is deceptions"))
			      .andExpect(jsonPath("$.losingTeamSurvivors").value("Transformer10, Transformer1, Transformer6"))
			      .andExpect(jsonPath("$.details").isEmpty());
	}
	
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
